package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.Sessions;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionsRepository extends CrudRepository<Sessions, String> {
    Optional<Sessions> findByToken(@Param("token") String token);
    @Transactional
    void deleteByToken(@Param("token") String token);
}
