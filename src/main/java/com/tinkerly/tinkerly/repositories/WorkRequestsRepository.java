package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkRequests;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkRequestsRepository extends CrudRepository<WorkRequests, String> {
    Optional<WorkRequests> findByRequestId(@Param("requestId") String requestId);
    List<WorkRequests> findAllByWorkerId(@Param("workerId") String workerId);
    List<WorkRequests> findAllByCustomerId(@Param("customerId") String customerId);
    boolean existsByRequestId(@Param("requestId") String requestId);

    @Transactional
    void deleteByRequestId(@Param("requestId") String requestId);
}
