package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.AdministratorProfiles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdministratorProfileRepository extends CrudRepository<AdministratorProfiles, String> {
    Optional<AdministratorProfiles> findByUserId(@Param("userId") String userId);
}
