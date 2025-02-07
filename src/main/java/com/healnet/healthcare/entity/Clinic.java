package com.healnet.healthcare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clinic")
public class Clinic extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "hospital", referencedColumnName = "id")
    private Hospital hospital;

    @Column(name = "name")
    private String clinicName;

    @Column(name = "is_deleted", columnDefinition="tinyint(1) default 0")
    private boolean isDeleted;
}
