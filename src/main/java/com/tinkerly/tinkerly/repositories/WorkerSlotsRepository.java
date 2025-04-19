package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkerSlots;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface WorkerSlotsRepository extends CrudRepository<WorkerSlots, String> {
    ArrayList<WorkerSlots> findAllByWorkerId(@Param("workerId") String workerId);
}
