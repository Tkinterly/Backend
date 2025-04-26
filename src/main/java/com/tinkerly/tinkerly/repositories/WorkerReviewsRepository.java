package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkerReviews;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkerReviewsRepository extends CrudRepository<WorkerReviews, String> {
    List<WorkerReviews> findByWorkerId(String workerId);
}
