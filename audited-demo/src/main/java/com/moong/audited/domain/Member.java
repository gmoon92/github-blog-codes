package com.moong.audited.domain;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
