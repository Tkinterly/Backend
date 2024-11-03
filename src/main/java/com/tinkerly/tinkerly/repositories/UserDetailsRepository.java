package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailsRepository extends CrudRepository<UserDetails, String> {
    Optional<UserDetails> findByUserId(@Param("userId") String userId);
}
