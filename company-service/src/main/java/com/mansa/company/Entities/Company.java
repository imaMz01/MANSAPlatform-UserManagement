package com.mansa.company.Entities;

import com.mansa.company.Enums.Type;
import com.mansa.company.Utils.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Company extends AbstractEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String sector;
    private String address;
    private String description;
    private String tel;
    private String email;
    private LocalDate creationDate;
    private String ca;
    private String userId;
}
