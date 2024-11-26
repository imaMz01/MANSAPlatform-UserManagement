package com.mansa.user.Services.InvitationService;

import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Dtos.UserDto;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface InvitationService {

    InvitationDto sendInvitation(InvitationDto invitationDto);
    List<InvitationDto> invitations();
    InvitationDto invitationById(String id);
    String verifyInvitation(String token);
    List<InvitationDto> adminInvitations();
    void generateAdminInvitationVerificationTokenAndSendEmail(String id, String email);
}
