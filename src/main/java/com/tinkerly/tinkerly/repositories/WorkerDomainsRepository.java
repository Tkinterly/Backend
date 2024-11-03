package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkerDomains;
import com.tinkerly.tinkerly.entities.WorkerProfiles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkerDomainsRepository extends CrudRepository<WorkerDomains, String> {
    List<WorkerDomains> findAllByUserId(@Param("userId") String userId);
}
