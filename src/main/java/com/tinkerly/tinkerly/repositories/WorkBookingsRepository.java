package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.WorkBookings;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkBookingsRepository extends CrudRepository<WorkBookings, String> {
    Optional<WorkBookings> findByBookingId(@Param("bookingId") String bookingId);

    @Transactional
    void deleteByBookingId(@Param("bookingId") String bookingId);
}
