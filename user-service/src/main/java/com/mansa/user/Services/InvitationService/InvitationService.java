package com.mansa.user.Services.InvitationService;

import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Enums.InvitationType;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface InvitationService {

    InvitationDto sendInvitation(InvitationDto invitationDto, InvitationType type);
    List<InvitationDto> invitations();
    InvitationDto invitationById(String id);
    String verifyInvitation(String token);
    List<InvitationDto> adminInvitations();
    void generateInvitationVerificationTokenAndSendEmail(String id, String email,String type);
    ModelAndView verifyInvit(String token);
    String verify(String token);

}
