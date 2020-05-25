package com.moong.audited.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Audited
@Setter @Getter
@ToString
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public static Member newMember(String name) {
        Member newMember = new Member();
        newMember.setName(name);
        return newMember;
    }
}
