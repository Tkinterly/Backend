package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.Credentials;
import com.tinkerly.tinkerly.entities.Sessions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, String> {
    Optional<Credentials> findOneByUsername(@Param("username") String username);
    boolean existsByUsername(@Param("username") String username);
}
