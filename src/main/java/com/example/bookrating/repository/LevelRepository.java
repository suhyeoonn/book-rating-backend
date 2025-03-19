package com.example.bookrating.repository;

import com.example.bookrating.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LevelRepository extends JpaRepository<Level, Long> {
    // 여러 개의 ID로 레벨을 조회
    Set<Level> findAllByIdIn(List<Long> ids);
}
