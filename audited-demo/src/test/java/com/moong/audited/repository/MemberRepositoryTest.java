package com.moong.audited.repository;

import com.moong.audited.core.BaseTestCase;
import com.moong.audited.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@Slf4j
@DataJpaTest
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
class MemberRepositoryTest extends BaseTestCase {

    private final MemberRepository memberRepository;

    private final EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void init() {
        isAutoFlushAndClear = false;
    }

    @Test
    void testSave() {
        Member member = new Member();
        member.setName("gmoon");
        memberRepository.save(member);
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    void testAuditedReaderFind() {
        isAutoFlushAndClear = false;
        Member gmoon = memberRepository.save(Member.newMember("gmoon"));
        Long entityId = gmoon.getId();
        log.info("entity id : {}", entityId);

        Number revisionNumber = getLatestRevisionNumber(Member.class, entityId);
        log.info("revision number : {}", revisionNumber);
        Member memberAud = getAuditReader().find(Member.class, entityId, revisionNumber);
        log.info("audit member : {}", memberAud);
    }

    @Test
    @Transactional(propagation = NOT_SUPPORTED)
    void testRetrieveLatestRevisionNumber() {
        isAutoFlushAndClear = false;
        Member gmoon = memberRepository.save(Member.newMember("gmoon"));
        Long entityId = gmoon.getId();
        log.info("entity id : {}", entityId);

        Number revisionNumber = (Number) getAuditReader().createQuery()
                .forRevisionsOfEntity(Member.class, true, false)
                .addProjection(AuditEntity.revisionNumber().max())
                .add(AuditEntity.id().eq(entityId))
                .add(AuditEntity.revisionType().eq(RevisionType.ADD))
                .add(AuditEntity.property("name").eq("gmoon"))
                .getSingleResult();

        log.info("revisionNumber : {}", revisionNumber);
    }

    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(getEntityManager());
    }

    private <Entity> Number getLatestRevisionNumber(Class<Entity> clazz, Long entityId) {
        return (Number) getAuditReader().createQuery()
                    .forRevisionsOfEntity(clazz, false, true)
                    .addProjection(AuditEntity.revisionNumber().max())
                    .add(AuditEntity.id().eq(entityId))
                    .getSingleResult();
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}