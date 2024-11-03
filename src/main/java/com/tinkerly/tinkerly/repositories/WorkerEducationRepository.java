package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkerEducation;
import com.tinkerly.tinkerly.entities.WorkerProfiles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkerEducationRepository extends CrudRepository<WorkerEducation, String> {
    List<WorkerEducation> findAllByUserId(@Param("userId") String userId);
}
