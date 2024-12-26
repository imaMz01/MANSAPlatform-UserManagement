package com.mansa.user.Services.InvitationService;

import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Entities.User;
import com.mansa.user.Enums.InvitationType;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface InvitationService {

    InvitationDto sendInvitation(InvitationDto invitationDto, String idData) throws Exception;
    List<InvitationDto> invitations();
    InvitationDto invitationById(String id);
    String verifyInvitation(String token);
    List<InvitationDto> adminInvitations();
    void generateInvitationVerificationTokenAndSendEmail(String id, String email, String type, String idData, User user) throws Exception;
    ModelAndView verifyInvit(String token);
    String verify(String token);
    String assign(String idData,String idUser,String token);

}
