package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkBookings;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkBookingsRepository extends CrudRepository<WorkBookings, String> {
    Optional<WorkBookings> findByBookingId(@Param("bookingId") String bookingId);
    List<WorkBookings> findAllByWorkerId(@Param("workerId") String workerId);
    List<WorkBookings> findAllByCustomerId(String customerId);

    @Transactional
    void deleteByBookingId(@Param("bookingId") String bookingId);

}
