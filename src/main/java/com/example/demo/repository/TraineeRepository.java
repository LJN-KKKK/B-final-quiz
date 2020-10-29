package com.example.demo.repository;

import com.example.demo.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    List<Trainee> findAllByGrouped(boolean grouped);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update trainee t set t.grouped=?2 where t.id=?1", nativeQuery = true)
    void updateGroupedById(Long id, boolean grouped);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update trainee t set t.group_id=?2 where t.id=?1", nativeQuery = true)
    void updateGroupById(Long id, Long groupId);
}
