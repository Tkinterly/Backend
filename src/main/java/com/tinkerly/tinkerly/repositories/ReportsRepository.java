package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.Reports;
import com.tinkerly.tinkerly.entities.WorkDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportsRepository extends CrudRepository<Reports, String> {
    List<Reports> findAll(Pageable pageable);

    @Transactional
    void deleteByReportId(@Param("reportId") String reportId);
}
