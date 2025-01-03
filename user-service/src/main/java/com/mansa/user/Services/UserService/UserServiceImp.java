package com.mansa.user.Services.UserService;

import com.mansa.user.Dtos.*;
import com.mansa.user.Entities.Role;
import com.mansa.user.Entities.User;
import com.mansa.user.Exceptions.*;
import com.mansa.user.FeignClient.SubscriptionFeign.SubscriptionFeign;
import com.mansa.user.Mappers.UserMapper;
import com.mansa.user.Repositories.UserRepository;
import com.mansa.user.Security.JwtTokenProvider;
import com.mansa.user.Services.RoleService.RoleService;
import com.mansa.user.Util.PasswordGenerator;
import com.mansa.user.Util.Statics;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static com.mansa.user.Mappers.RoleMapper.roleMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    @Value("${token.signing.key}")
    private String secretKey;
    private final JavaMailSender mailSender;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final SubscriptionFeign subscriptionFeign;
    private final StreamBridge streamBridge;


    @Override
    public UserDto add(UserDto userDto) {
        if(checkEmail(userDto.getEmail())){
            throw  new EmailAlreadyExistException(userDto.getEmail());
        }
        User user = userMapper.toEntity(userDto);
        user.setId(UUID.randomUUID().toString());
        user.setCreated(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setEmailVerified(false);
        if(userDto.getCompanyName() == null)
            user.setCompanyName("");
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        RoleDto roleDto = roleService.getByRole(Statics.DEFAULT_ROLE);
        if(roleDto != null) user.getRoles().add(roleMapper.toEntity(roleDto));
        return userMapper.toDto(
                userRepository.save(user)
        );
    }
    public boolean checkEmail(String email){
        return userRepository.findByEmailIgnoreCase(email).isPresent();
    }

    @Override
    public User userByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(
                ()->new UserWithEmailNotFoundException(email)
        );
    }

    @Override
    public UserDto createInviteAccount(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setId(UUID.randomUUID().toString());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setCreated(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(roleMapper.toEntity(roleService.getByRole(Statics.ADMIN_ROLE)));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> usersByCompany(String name) {
        return userMapper.toDto(userRepository.findByCompanyNameIgnoreCase(name));
    }

    @Override
    public String createAccount(String email) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setCreated(LocalDateTime.now());
        user.setEmail(email);
        user.setCreated(LocalDateTime.now());
        String password = PasswordGenerator.generatePassword(10);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return password;
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = getUser(getCurrentUser().getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setTel(userDto.getTel());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAddress(userDto.getAddress());
        user.setCreated(userDto.getCreated());
        user.setUpdated(LocalDateTime.now());
        user.setCompanyName(userDto.getCompanyName());
        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public List<UserDto> all() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    public UserDto changeStatus(String id) {
        User user = getUser(id);
        user.setEnabled(!user.isEnabled());
        user.setUpdated(LocalDateTime.now());
        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public UserDto getById(String id) {
        return userMapper.toDto(
                getUser(id)
        );
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) throws Exception {

        // Find user by email
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(InvalidEmailOrPasswordException::new);
        if(!user.isEmailVerified()) throw new EmailNotVerifiedException();
        // Authenticate using email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        log.info("User authenticated");

        log.info("User found: {}", user.getEmail());
        String jwt = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        log.info("Access token generated: {}", jwt);
        log.info("Refresh token generated: {}",refreshToken);

        log.info("User return object: {}",JwtAuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .userDto(userMapper.toDto(user))
                .build());
        return JwtAuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .userDto(userMapper.toDto(user))
                .build();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Processing logout...");
        request.getSession().invalidate();
        log.info("Session invalidated.");
        SecurityContextHolder.clearContext();
        log.info("Security context cleared.");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setValue(null);
                response.addCookie(cookie);
                log.info("Cookie '{}' deleted.", cookie.getName());
            }
        }
    }

    @Override
    public String generateEmailVerificationTokenAndSendEmail(String id) {
        String token= jwtTokenProvider.generateEmailVerificationToken(id);
        VerificationRequest request = new VerificationRequest(getUser(id).getLastName(),
                getUser(id).getEmail(),token,"");
        streamBridge.send("VerificationEmail-topic",request);
        return token;
    }

    @Override
    public String verifyEmail(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String id = claims.getSubject();
            User user = getUser(id);
            if(user.isEmailVerified()){
                throw new EmailAlreadyVerifiedException(user.getEmail());
            }
            user.setEmailVerified(true);
            userRepository.save(user);
            return "Email verified successfully.";
        }catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            log.info("id is : {}",e.getClaims().getSubject());
            generateEmailVerificationTokenAndSendEmail(e.getClaims().getSubject());
            throw new TokenExpiredException();
        }  catch (JwtException e) {
            log.info("error : {}",e.getMessage());
            throw new TokenInvalidException();
        }
    }

    private User getUser(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            Object principal = authentication.getPrincipal();
            if(principal instanceof User){
                return userMapper.toDto((User)principal);
            }
        }
        return null;
    }

    @Override
    public UserDto addAuthority(String id, String role) {
        User user = getUser(id);
        RoleDto roleDto = roleService.getByRole(role);
        if(roleDto != null){
            Role roleEntity = roleMapper.toEntity(roleDto);
//            if(user.getRoles().stream().anyMatch(
//                    role1 -> role1.getId().equals(roleEntity.getId())
//            ))
//                throw new UserHasAlreadyThisRoleExistException(id,roleEntity.getRole());
            user.getRoles().add(roleEntity);
        }

        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public UserDto removeAuthority(String id, String role) {

        User user = getUser(id);
        RoleDto roleDto = roleService.getByRole(role);
        log.info("roles --------------> {}",user.getRoles().size());
        if(roleDto != null){
            Role roleEntity = roleMapper.toEntity(roleDto);
            user.getRoles().removeIf( role1 -> role1.getId().equals(roleEntity.getId()));
        }

        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public List<SubscriptionDto> userSubscriptions() {
        return subscriptionFeign.userSubscriptions(getCurrentUser().getId()).getBody();
    }


}
