package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkerProfiles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerProfileRepository extends CrudRepository<WorkerProfiles, String> {
    Optional<WorkerProfiles> findByUserId(@Param("userId") String userId);
    boolean existsByUserId(@Param("userId") String userId);
}
