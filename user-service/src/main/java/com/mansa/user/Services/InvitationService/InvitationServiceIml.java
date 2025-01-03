package com.mansa.user.Services.InvitationService;

import com.mansa.user.Dtos.*;
import com.mansa.user.Entities.Invitation;
import com.mansa.user.Entities.User;
import com.mansa.user.Enums.InvitationType;
import com.mansa.user.Exceptions.*;
import com.mansa.user.FeignClient.DataFeign.DataFeign;
import com.mansa.user.Mappers.InvitationMapper;
import com.mansa.user.Mappers.UserMapper;
import com.mansa.user.Repositories.InvitationRepository;
import com.mansa.user.Security.JwtTokenProvider;
import com.mansa.user.Services.UserService.UserService;
import com.mansa.user.Util.KeyLoader;
import com.mansa.user.Util.Statics;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceIml implements InvitationService{

    private final InvitationRepository invitationRepository;
    private final StreamBridge streamBridge;
    private final InvitationMapper mapper;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final DataFeign dataFeign;
    private final KeyLoader keyLoader;
    @Value("${token.signing.key}")
    private String secretKey;
    @Value("${token.signing.public-key}")
    private String publicKeyPath;


    @Override
    @Transactional
    public InvitationDto sendInvitation(InvitationDto invitationDto, String idData) throws Exception {
        Invitation invitation = mapper.toEntity(invitationDto);
        User user =userService.userByEmail(invitation.getEmail());
        if(invitation.getType().equals(InvitationType.CHECKER_INVITATION)) {
            DataDto dataDto = dataFeign.dataById(idData).getBody();
            if (dataDto!=null && dataDto.getMaker().getId().equals(user.getId()))
                throw new CheckerAndMakerAreIdenticalException();
        }
        if(invitation.getType().equals(InvitationType.ADMIN_INVITATION) && userService.checkEmail(invitation.getEmail()) && user.getRoles().stream().anyMatch(
                role1 -> role1.getRole().equals(Statics.ADMIN_ROLE)
        ))
            throw new UserHasAlreadyThisRoleExistException(userService.userByEmail(invitation.getEmail()).getId(),Statics.ADMIN_ROLE);
        if(invitation.getType().equals(InvitationType.MAKER_INVITATION) && userService.checkEmail(invitation.getEmail()) && user.getRoles().stream().anyMatch(
                role1 -> role1.getRole().equals(Statics.MAKER_ROLE)
        ))
            throw new UserHasAlreadyThisRoleExistException(userService.userByEmail(invitation.getEmail()).getId(),Statics.MAKER_ROLE);

//        if(invitation.getType().equals(InvitationType.CHECKER_INVITATION) && userService.checkEmail(invitation.getEmail()) && user.getRoles().stream().anyMatch(
//                role1 -> role1.getRole().equals(Statics.CHECKER_ROLE)
//        ))
//            throw new UserHasAlreadyThisRoleExistException(userService.userByEmail(invitation.getEmail()).getId(),Statics.CHECKER_ROLE);

        invitation.setId(UUID.randomUUID().toString());
        invitation.setInvitedBy(userMapper.toEntity(userService.getCurrentUser()));
        invitation.setType(invitation.getType());
        InvitationDto invitationSaved = mapper.toDto(invitationRepository.save(invitation));
        log.info("email --->{}",invitationSaved.getEmail());
        this.generateInvitationVerificationTokenAndSendEmail(invitationSaved.getId(),invitationSaved.getEmail(),invitationSaved.getType().toString(),idData,user);
        return invitationSaved;
    }

    @Override
    public List<InvitationDto> invitations() {
        return mapper.toDto(invitationRepository.findAll());
    }

    @Override
    public InvitationDto invitationById(String id) {
        return mapper.toDto(getById(id));
    }

    public Invitation getById(String id){
        return invitationRepository.findById(id).orElseThrow(
                ()-> new InvitationNotFountException(id)
        );
    }

    @Override
    @Transactional
    public String verifyInvitation(String token) {
        try {
            log.info("token : {}",token );
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(keyLoader.loadPublicKey(publicKeyPath))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String id = claims.get("idInvitation", String.class);
            Invitation invitation = getById(id);
            log.info("invitation : {}",invitation.toString());
            if(invitation.isAccepted()){
                throw new InvitationAlreadyAcceptedException();
            }
            if(userService.checkEmail(invitation.getEmail())){
                invitation.setAccepted(true);
                userService.addAuthority(userService.userByEmail(invitation.getEmail()).getId(), Statics.ADMIN_ROLE);
                invitationRepository.save(invitation);
                return "You're an admin now";
            }
            invitation.setAccepted(true);
            invitationRepository.save(invitation);
            SignInRequest request = new SignInRequest(invitation.getEmail(),userService.createAccount(invitation.getEmail()));
            userService.addAuthority(userService.userByEmail(invitation.getEmail()).getId(), Statics.ADMIN_ROLE);
            streamBridge.send("consumeCredentialsEmail-topic",request);
            return "Please verify your inbox. You will receive an email containing your credentials.";
        }  catch (Exception e) {
            log.info("error : {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ModelAndView verifyInvit(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(keyLoader.loadPublicKey(publicKeyPath))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String idAdminInvitation = claims.get("idInvitation", String.class);
            log.info(idAdminInvitation);

            Invitation invitation = getById(idAdminInvitation);
            if(invitation.isAccepted()){
                return new ModelAndView("errorPage").addObject("message", "Invitation already accepted.");
                //throw new InvitationAlreadyAcceptedException();
            }
            invitation.setAccepted(true);
            invitationRepository.save(invitation);
            if (userService.checkEmail(invitation.getEmail())) {
                UserDto user = userMapper.toDto(userService.userByEmail(invitation.getEmail()));
                userService.addAuthority(user.getId(), Statics.ADMIN_ROLE);

                return new ModelAndView("successPage")
                        .addObject("message", "Welcome to the Mansa platform, You are now an admin.")
                        .addObject("name", user.getLastName());
            } else {

                ModelAndView modelAndView = new ModelAndView("createAccountForm");
                modelAndView.addObject("email", invitation.getEmail());
                return modelAndView;
            }

        } catch (Exception e) {
            log.error("Error verifying invitation: {}", e.getMessage(), e);
            return new ModelAndView("error").addObject("message", "Invalid invitation token.");
        }
    }

    @Override
    public String verify(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(keyLoader.loadPublicKey(publicKeyPath))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String type = claims.get("type",String.class);
            String id = claims.get("idInvitation", String.class);
            Invitation invitation = getById(id);
            log.info("invitation : {}",invitation.toString());
            if(invitation.isAccepted()){
                throw new InvitationAlreadyAcceptedException();
            }
            if (userService.checkEmail(invitation.getEmail())) {
                UserDto user = userMapper.toDto(userService.userByEmail(invitation.getEmail()));
                if(type.equals(InvitationType.MAKER_INVITATION.toString())) {
                    invitation.setAccepted(true);
                    invitationRepository.save(invitation);
                    userService.addAuthority(user.getId(), Statics.MAKER_ROLE);
                    return "You're a maker now";
                }
                else if(type.equals(InvitationType.CHECKER_INVITATION.toString())) {
                    log.info("process : assigning");
                    ResponseEntity<String> response = dataFeign.assignCheckerToData(claims.get("idData",String.class), user.getId(),"Bearer "+token);
                    if (response.getStatusCode().equals(HttpStatus.OK)) {
                        log.info("process : assigning with success");
                        invitation.setAccepted(true);
                        invitationRepository.save(invitation);
                        userService.addAuthority(user.getId(), Statics.CHECKER_ROLE);
                        log.info("process : success");
                        return "You're a checker now";
                    }
                    return "Error";
                }
            }
            } catch (Exception e) {
                log.info("error : {}",e.getMessage());
                throw new RuntimeException(e.getMessage());
        }
        return "Error verifying invitation";
    }

    @Override
    public String assign(String idData, String idUser, String token) {
        ResponseEntity<String> response = dataFeign.assignCheckerToData(idData,idUser,"Bearer "+token);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            return "OK";
        }
        return "Error";
    }

    @Override
    public List<InvitationDto> adminInvitations() {
        return mapper.toDto(invitationRepository.findByInvitedBy(
                userMapper.toEntity(userService.getCurrentUser())
        ));
    }



    @Override
    public void generateInvitationVerificationTokenAndSendEmail(String id,String email,String type,String idData,User user) throws Exception {
        String token = jwtTokenProvider.generateInvitationVerificationToken(id,type,idData,email,user);
        VerificationRequest request = new VerificationRequest("",email,token,type);
        streamBridge.send("VerificationAdminInvitation-topic",request);
    }


}
