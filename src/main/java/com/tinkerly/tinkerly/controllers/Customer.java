package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.PriceGenerator;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class Customer extends SessionController {

    private final ProfileGenerator profileGenerator;
    private final UserBookingsRepository userBookingsRepository;
    private final WorkBookingsRepository workBookingsRepository;
    private final WorkDetailsRepository workDetailsRepository;
    private final WorkRequestsRepository workRequestsRepository;
    private final BidRequestsRepository bidRequestsRepository;

    public Customer(
            ProfileGenerator profileGenerator,
            SessionsRepository sessionsRepository,
            UserBookingsRepository userBookingsRepository,
            WorkBookingsRepository workBookingsRepository,
            WorkDetailsRepository workDetailsRepository,
            WorkRequestsRepository workRequestsRepository,
            BidRequestsRepository bidRequestsRepository
    ) {
        super(sessionsRepository);
        this.profileGenerator = profileGenerator;
        this.userBookingsRepository = userBookingsRepository;
        this.workBookingsRepository = workBookingsRepository;
        this.workDetailsRepository = workDetailsRepository;
        this.workRequestsRepository = workRequestsRepository;
        this.bidRequestsRepository = bidRequestsRepository;
    }

    @GetMapping("/customer/{customerId}")
    public EndpointResponse<Profile> getProfile(@PathVariable String customerId) {
        if (!this.isValidSession()) {
            // Invalid session
            return EndpointResponse.failed("Invalid session!");
        }

        Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(customerId);

        if (customerProfile.isEmpty()) {
            return EndpointResponse.failed("Invalid customer!");
        }

        return EndpointResponse.passed(customerProfile.get());
    }

    @GetMapping("/customer/bookings")
    public EndpointResponse<ListingsResponse<UserBooking>> getBookings() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String customerId = sessions.get().getUserId();

        List<UserBookings> userBookings = this.userBookingsRepository.findAllByCustomerId(customerId);

        List<UserBooking> bookings = new ArrayList<UserBooking>();
        for (UserBookings userBooking : userBookings) {
            Optional<WorkBookings> workBookingsQuery = this.workBookingsRepository.findByBookingId(userBooking.getBookingId());
            if (workBookingsQuery.isEmpty()) {
                continue;
            }

            WorkBookings workBookingEntry = workBookingsQuery.get();

            if (!workDetailsRepository.existsById(workBookingEntry.getWorkDetailsId())) {
                continue;
            }

            String workerId = workBookingEntry.getWorkerId();
            Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);

            if (workerProfile.isEmpty()) {
                continue;
            }

            WorkBooking workBooking = new WorkBooking(workBookingEntry, workerProfile.get());

            bookings.add(
                    new UserBooking(userBooking, workBooking)
            );
        }

        List<BidRequest> bidRequests = new ArrayList<>();
        List<WorkRequests> workRequests = this.workRequestsRepository.findAllByCustomerId(customerId);

        for (WorkRequests workRequest : workRequests) {
            Optional<WorkDetails> workDetailsQuery = this.workDetailsRepository.findById(workRequest.getWorkDetailsId());

            Profile workerProfile = this.profileGenerator.getWorkerProfile(workRequest.getWorkerId()).orElse(null);

            if (workDetailsQuery.isPresent() && workerProfile != null) {
                String bookingId = workRequest.getRequestId();
                int price = PriceGenerator.generate(
                        workDetailsQuery.get().getRecommendedPrice(),
                        workerProfile.getWorkerProfile().getYearsOfExperience(),
                        workerProfile.getRegistrationDate()
                );

                WorkBooking workBooking = new WorkBooking(
                        bookingId,
                        workRequest.getWorkDetailsId(),
                        workRequest.getBiddingTier(),
                        price,
                        workerProfile
                );

                bookings.add(new UserBooking(bookingId, 0, workBooking));
            }

            Optional<BidRequests> bidRequest = this.bidRequestsRepository.findByRequestId(workRequest.getRequestId());
            bidRequest.ifPresent(requests -> bidRequests.add(new BidRequest(requests)));
        }

        return EndpointResponse.passed(new ListingsResponse<>(bookings, bidRequests));
    }
}
