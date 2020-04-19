package com.moong.audit.repo;

import com.moong.audit.entities.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@Slf4j
@DataJpaTest
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
class MemberRepositoryTest {

    private final EntityManager em;

    private final MemberRepository memberRepository;

    @Test
    void saveAutoAuditedFields() {
        Member member = memberRepository.save(Member.builder()
                .userId("gmoon")
                .password("111111")
                .build());

        assertAll("auto init audited fields",
                () -> assertNotNull(member.getCreatedBy()),
                () -> assertNotNull(member.getCreatedDt()),
                () -> assertNotNull(member.getModifiedBy()),
                () -> assertNotNull(member.getModifiedDt())
        );
    }

    @TestConfiguration
    @EnableJpaAuditing
    static class TestAuditedConfig {

        @Bean
        public AuditorAware<Long> auditorAware() {
            return () -> Optional.ofNullable(0L);
        }
    }

    @AfterEach
    void tear() {
        entityManagerFlushAndClear();
    }

    private void entityManagerFlushAndClear() {
        em.flush();
        em.clear();
    }
}