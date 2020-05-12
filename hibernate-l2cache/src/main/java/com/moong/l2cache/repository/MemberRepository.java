package com.moong.l2cache.repository;

import com.moong.l2cache.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
