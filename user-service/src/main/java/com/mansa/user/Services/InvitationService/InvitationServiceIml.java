package com.mansa.user.Services.InvitationService;

import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Dtos.VerificationRequest;
import com.mansa.user.Entities.Invitation;
import com.mansa.user.Entities.User;
import com.mansa.user.Exceptions.*;
import com.mansa.user.Mappers.InvitationMapper;
import com.mansa.user.Mappers.UserMapper;
import com.mansa.user.Repositories.InvitationRepository;
import com.mansa.user.Security.JwtTokenProvider;
import com.mansa.user.Services.UserService.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceIml implements InvitationService{

    private final InvitationRepository invitationRepository;
    private final StreamBridge streamBridge;
    private final InvitationMapper mapper;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    @Value("${token.signing.key}")
    private String secretKey;

    @Override
    @Transactional
    public InvitationDto sendInvitation(InvitationDto invitationDto) {
        Invitation invitation = mapper.toEntity(invitationDto);
        invitation.setId(UUID.randomUUID().toString());
        invitation.setUser(userMapper.toEntity(userService.getCurrentUser()));
        InvitationDto invitationSaved = mapper.toDto(invitationRepository.save(invitation));
        generateAdminInvitationVerificationTokenAndSendEmail(invitationSaved.getId(),invitationSaved.getEmail());
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
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            String id = claims.getSubject();
//            Invitation invitation = getById(id);
//            if(invitation.isAccepted()){
//                throw new InvitationAlreadyAcceptedException();
//            }
//            invitation.setAccepted(true);
//            if(userService.checkEmail(invitation.getEmail()))
//            userRepository.save(user);
//            return "Email verified successfully.";
//        }catch (ExpiredJwtException e) {
//            System.out.println(e.getMessage());
//            log.info("id is : {}",e.getClaims().getSubject());
//            generateEmailVerificationTokenAndSendEmail(e.getClaims().getSubject());
//            throw new TokenExpiredException();
//        }  catch (JwtException e) {
//            log.info("error : {}",e.getMessage());
//            throw new TokenInvalidException();
//        }
        return "";
    }

    @Override
    public List<InvitationDto> adminInvitations() {
        return mapper.toDto(invitationRepository.findByUser(
                userMapper.toEntity(userService.getCurrentUser())
        ));
    }

    @Override
    public void generateAdminInvitationVerificationTokenAndSendEmail(String id,String email) {
        String token = jwtTokenProvider.generateAdminInvitationVerificationToken(id);
        VerificationRequest request = new VerificationRequest("",email,token);
        streamBridge.send("VerificationAdminInvitation-topic",request);
    }


}
