package com.moong.l2cache.repository;

import com.moong.l2cache.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
