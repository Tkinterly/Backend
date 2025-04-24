package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkResponses;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkResponsesRepository extends CrudRepository<WorkResponses, String> {
    Optional<WorkResponses> findByWorkRequestId(@Param("workRequestId") String requestId);
    boolean existsByWorkRequestId(@Param("workRequestId") String requestId);

    @Transactional
    void deleteByWorkRequestId(@Param("workRequestId") String requestId);
}

