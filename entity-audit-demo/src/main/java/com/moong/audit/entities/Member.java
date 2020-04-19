package com.moong.audit.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTraceEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String password;

    @Builder
    private Member(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
