package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.Reports;
import com.tinkerly.tinkerly.entities.WorkDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportsRepository extends CrudRepository<Reports, String> {
    List<Reports> findAll(Pageable pageable);
}
