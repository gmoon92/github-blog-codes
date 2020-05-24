package com.moong.l2cache.base;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
public class BaseRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @AfterEach
    public void tear() {
        flushAndClear();
    }
}
