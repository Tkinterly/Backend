package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.Profiles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profiles, String> {
    Optional<Profiles> findByUserId(@Param("userId") String userId);
}
