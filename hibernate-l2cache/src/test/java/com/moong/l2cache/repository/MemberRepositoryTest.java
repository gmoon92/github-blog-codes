package com.moong.l2cache.repository;

import com.moong.l2cache.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@Slf4j
@DataJpaTest
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
class MemberRepositoryTest {

    private final MemberRepository memberRepository;

    private final EntityManager entityManager;

    @Test
    void testSave() {
        Member member = memberRepository.save(Member.newInstance("moong"));
        assertThat(member.getId()).isNotNull();
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