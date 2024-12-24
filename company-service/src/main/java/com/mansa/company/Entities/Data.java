package com.mansa.company.Entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mansa.company.Utils.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Data extends AbstractEntity {

    private String description;
    @ManyToOne
    private Company company;
    private String idMaker;
    private String idChecker;
    private String idAdmin;
    private boolean checked;
    private boolean published;

}
