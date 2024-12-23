package com.mansa.user.Entities;


import com.mansa.user.Enums.InvitationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Invitation {

    @Id
    private String id;
    private String email;
    private boolean accepted;
    @ManyToOne
    private User invitedBy;
    @Enumerated(EnumType.STRING)
    private InvitationType type;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
    private LocalDateTime updatedAt;
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
