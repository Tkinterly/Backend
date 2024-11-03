package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.CustomerProfiles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerProfileRepository extends CrudRepository<CustomerProfiles, String> {
    Optional<CustomerProfiles> findByUserId(@Param("userId") String userId);
}
