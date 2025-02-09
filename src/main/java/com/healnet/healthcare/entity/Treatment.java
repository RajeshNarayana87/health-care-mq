package com.healnet.healthcare.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "treatment")
public class Treatment extends BaseEntityWithId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "clinic", referencedColumnName = "id")
    private Clinic clinic;

    @Column(name = "name")
    private String treatmentName;

    @Setter
    @Column(name = "is_deleted", columnDefinition="tinyint(1) default 0")
    private boolean isDeleted;
}
