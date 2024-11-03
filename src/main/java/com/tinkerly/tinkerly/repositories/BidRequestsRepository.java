package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.BidRequests;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BidRequestsRepository extends CrudRepository<BidRequests, String> {
    Optional<BidRequests> findByRequestId(@Param("requestId") String requestId);

    @Transactional
    void deleteByRequestId(@Param("requestId") String requestId);
}
