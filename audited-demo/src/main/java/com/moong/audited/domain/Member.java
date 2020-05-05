package com.moong.audited.domain;

import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Audited
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
