package com.healnet.healthcare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "hospital")
public class Hospital extends BaseEntity implements Serializable {
    @Column(name = "name")
    private String hospitalName;

    @Column(name = "is_deleted", columnDefinition="tinyint(1) default 1")
    private boolean isDeleted;
}
