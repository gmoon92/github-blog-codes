package com.moong.l2cache.repository;

import com.moong.l2cache.base.BaseRepositoryTest;
import com.moong.l2cache.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@Slf4j
@DataJpaTest
@TestConstructor(autowireMode = ALL)
@RequiredArgsConstructor
class TeamRepositoryTest extends BaseRepositoryTest {

    private final TeamRepository teamRepository;

    @Test
    void testSave() {
        Team team = Team.newTeam("web1");
        teamRepository.save(team);
    }


}