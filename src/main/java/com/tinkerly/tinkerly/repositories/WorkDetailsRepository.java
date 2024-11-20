package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkDetailsRepository extends CrudRepository<WorkDetails, String> {
    List<WorkDetails> findAllByDomain(@Param("domain") int domain);
}
