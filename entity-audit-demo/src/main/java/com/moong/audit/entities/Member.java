package com.moong.audit.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String password;

    @CreatedBy
    private Long createdBy;

    @CreatedDate
    private LocalDateTime createdDt;

    @LastModifiedBy
    private Long modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedDt;

    @Builder
    private Member(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
