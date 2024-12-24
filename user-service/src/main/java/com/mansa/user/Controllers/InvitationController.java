package com.mansa.user.Controllers;


import com.mansa.user.Annotations.RequestLogger;
import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Dtos.UserDto;
import com.mansa.user.Enums.InvitationType;
import com.mansa.user.Services.InvitationService.InvitationService;
import com.mansa.user.Services.UserService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class InvitationController {

    private final InvitationService invitationService;
    private final UserService userService;

    @RequestLogger(action = "invite admin")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @PostMapping("/admin/invite")
    public ResponseEntity<InvitationDto> inviteAdmin(@Valid @RequestBody InvitationDto invitationDto){
        return new ResponseEntity<>(invitationService.sendInvitation(invitationDto, InvitationType.ADMIN_INVITATION), HttpStatus.CREATED);
    }

    @RequestLogger(action = "invite maker or checker")
    @PreAuthorize("hasRole(@Statics.DEFAULT_ROLE) or hasRole(@Statics.SUBSCRIBER_ROLE)")
    @PostMapping("/invite/{type}")
    public ResponseEntity<InvitationDto> inviteMakerOrChecker(@Valid @RequestBody InvitationDto invitationDto,@PathVariable InvitationType type){
        return new ResponseEntity<>(invitationService.sendInvitation(invitationDto, type), HttpStatus.CREATED);
    }

//    @PostMapping("/checker/invite")
//    public ResponseEntity<InvitationDto> inviteChecker(@Valid @RequestBody InvitationDto invitationDto){
//        return new ResponseEntity<>(invitationService.sendInvitation(invitationDto, InvitationType.CHECKER_INVITATION), HttpStatus.CREATED);
//    }

    @GetMapping("/admin/invite/verify/{token}")
    public ResponseEntity<String> verifyAdminInvitation(@PathVariable String token){
        return new ResponseEntity<>(invitationService.verifyInvitation(token),HttpStatus.OK);
    }

    @GetMapping("/invite/verify/{token}")
    public ResponseEntity<String> verifyMakerOrCheckerInvitation(@PathVariable String token){
        return new ResponseEntity<>(invitationService.verify(token),HttpStatus.OK);
    }

//    @GetMapping("/admin/invite/verify/{token}")
//    public ModelAndView verifyInvitation(@PathVariable("token") String token) {
//        return invitationService.verifyInvit(token);
//    }

    @PostMapping("/users/createInviteAccount")
    public ModelAndView createInviteAccount(
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String password) {

        try {

            if(userService.checkEmail(email)) return new ModelAndView("errorPage").addObject("message", "Invitation already accepted.");
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setFirstName(firstName);
            userDto.setLastName(lastName);
            userDto.setTel(phone);
            userDto.setAddress(address);
            userDto.setPassword(password);

            UserDto createdUser = userService.createInviteAccount(userDto);

            return new ModelAndView("successPage")
                    .addObject("message", "Account created successfully! Welcome to the Mansa platform, You are now an admin.")
                    .addObject("name", createdUser.getLastName());
        } catch (Exception e) {
            log.error("Error creating account: {}", e.getMessage(), e);
            return new ModelAndView("errorPage")
                    .addObject("message", "Failed to create account. Please try again.");
        }
    }

    @RequestLogger(action = "all invitations")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/invitations")
    public ResponseEntity<List<InvitationDto>> invitations(){
        return new ResponseEntity<>(invitationService.invitations(),HttpStatus.OK);
    }

    @RequestLogger(action = "admin invitations")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/adminInvitations")
    public ResponseEntity<List<InvitationDto>> adminInvitations(){
        return new ResponseEntity<>(invitationService.adminInvitations(),HttpStatus.OK);
    }

    @RequestLogger(action = "invitation by admin")
    @PreAuthorize("hasRole(@Statics.ADMIN_ROLE)")
    @GetMapping("/admin/inviteById/{id}")
    public ResponseEntity<InvitationDto> inviteById(@PathVariable String id){
        return new ResponseEntity<>(invitationService.invitationById(id),HttpStatus.OK);
    }
}
