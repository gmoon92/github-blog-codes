package com.moong.audited.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class BaseTestCase {

    @PersistenceContext
    protected EntityManager entityManager;

    protected boolean isAutoFlushAndClear = true;

    @AfterEach
    void tear() {
        if (isAutoFlushAndClear)
            flushAndClear();
    }

    protected void flushAndClear() {
        log.info("Entity Manager Execute flushAndClear...");
        entityManager.flush();
        entityManager.clear();
    }
}
