package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkerProfiles;
import com.tinkerly.tinkerly.entities.WorkerSkills;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkerSkillsRepository extends CrudRepository<WorkerSkills, String> {
    List<WorkerSkills> findAllByUserId(@Param("userId") String userId);
}
