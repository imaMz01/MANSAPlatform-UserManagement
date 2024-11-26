package com.mansa.user.Controllers;


import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Services.InvitationService.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InvitationController {

    private final InvitationService invitationService;

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PostMapping("/admin/invite")
    public ResponseEntity<InvitationDto> inviteAdmin(@Valid @RequestBody InvitationDto invitationDto){
        return new ResponseEntity<>(invitationService.sendInvitation(invitationDto), HttpStatus.CREATED);
    }

    @GetMapping("/admin/invite/verify/{token}")
    public ResponseEntity<String> verifyAdminInvitation(@PathVariable String token){
        return new ResponseEntity<>(invitationService.verifyInvitation(token),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/invitations")
    public ResponseEntity<List<InvitationDto>> invitations(){
        return new ResponseEntity<>(invitationService.invitations(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/adminInvitations")
    public ResponseEntity<List<InvitationDto>> adminInvitations(){
        return new ResponseEntity<>(invitationService.adminInvitations(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/inviteById/{id}")
    public ResponseEntity<InvitationDto> inviteById(@PathVariable String id){
        return new ResponseEntity<>(invitationService.invitationById(id),HttpStatus.OK);
    }
}
