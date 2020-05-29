package com.moong.audited.global;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

@Entity
@RevisionEntity
public class RevisionHistory extends DefaultRevisionEntity {

}
