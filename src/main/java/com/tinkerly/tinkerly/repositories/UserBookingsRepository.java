package com.tinkerly.tinkerly.repositories;

import com.tinkerly.tinkerly.entities.UserBookings;
import com.tinkerly.tinkerly.entities.WorkBookings;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserBookingsRepository extends CrudRepository<UserBookings, String> {
    Optional<UserBookings> findByBookingId(@Param("bookingId") String bookingId);
    List<UserBookings> findAllByCustomerId(@Param("customerId") String customerId);

    @Transactional
    void deleteByBookingId(@Param("bookingId") String bookingId);
}
