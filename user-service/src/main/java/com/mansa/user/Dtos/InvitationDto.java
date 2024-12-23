package com.mansa.user.Dtos;

import com.mansa.user.Enums.InvitationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDto {

    private String id;
    @NotNull(message = "Email should be not null")
    @NotEmpty(message = "Email should be not empty")
    private String email;
    private boolean accepted;
    private UserDto invitedBy;
    private InvitationType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
