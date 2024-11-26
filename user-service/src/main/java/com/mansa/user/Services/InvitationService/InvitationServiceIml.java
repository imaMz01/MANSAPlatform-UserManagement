package com.mansa.user.Services.InvitationService;

import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Dtos.SignInRequest;
import com.mansa.user.Dtos.VerificationRequest;
import com.mansa.user.Entities.Invitation;
import com.mansa.user.Entities.User;
import com.mansa.user.Exceptions.*;
import com.mansa.user.Mappers.InvitationMapper;
import com.mansa.user.Mappers.UserMapper;
import com.mansa.user.Repositories.InvitationRepository;
import com.mansa.user.Security.JwtTokenProvider;
import com.mansa.user.Services.UserService.UserService;
import com.mansa.user.Util.Statics;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
    @Value("${token.signing.key}")
    private String secretKey;

    @Override
    @Transactional
    public InvitationDto sendInvitation(InvitationDto invitationDto) {
        Invitation invitation = mapper.toEntity(invitationDto);
        invitation.setId(UUID.randomUUID().toString());
        invitation.setInvitedBy(userMapper.toEntity(userService.getCurrentUser()));
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
        try {
            log.info("token : {}",token );
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String id = claims.getSubject();
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
    public List<InvitationDto> adminInvitations() {
        return mapper.toDto(invitationRepository.findByInvitedBy(
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
