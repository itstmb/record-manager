package com.tmb.recordmanager.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "parent")
    private String parent;
}
