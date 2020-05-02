package com.moong.audited.repository;

import com.moong.audited.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;

import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@Slf4j
@DataJpaTest
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
class MemberRepositoryTest {

    private final EntityManager entityManager;

    private final MemberRepository memberRepository;

    @Test
    void testSave() {
        Member member = new Member();
        member.setName("gmoon");
        memberRepository.save(member);
    }

    @AfterEach
    void tear() {
        flushAndClear();
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}