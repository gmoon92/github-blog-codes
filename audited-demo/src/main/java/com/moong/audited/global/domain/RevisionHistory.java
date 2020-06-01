package com.moong.audited.global.domain;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "REVISION_HISTORY")
@RevisionEntity
public class RevisionHistory extends DefaultRevisionEntity {

    @Column(name = "username")
    private String username;
}
