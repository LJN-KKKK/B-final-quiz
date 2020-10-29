package com.example.demo.repository;

import com.example.demo.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    List<Trainer> findAllByGrouped(boolean grouped);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update trainer t set t.grouped=?2 where t.id=?1", nativeQuery = true)
    void updateGroupedById(Long id, boolean grouped);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update trainer t set t.group_id=?2 where t.id=?1", nativeQuery = true)
    void updateGroupIdById(Long id, Long groupId);
}
