package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.PriceGenerator;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class Customer extends SessionController {

    private final WorkBookingsRepository workBookingsRepository;
    private final WorkDetailsRepository workDetailsRepository;
    private final WorkRequestsRepository workRequestsRepository;
    private final WorkResponsesRepository workResponsesRepository;

    public Customer(
            ProfileGenerator profileGenerator,
            SessionsRepository sessionsRepository,
            WorkBookingsRepository workBookingsRepository,
            WorkDetailsRepository workDetailsRepository,
            WorkRequestsRepository workRequestsRepository,
            WorkResponsesRepository workResponsesRepository
    ) {
        super(sessionsRepository, profileGenerator);
        this.workBookingsRepository = workBookingsRepository;
        this.workDetailsRepository = workDetailsRepository;
        this.workRequestsRepository = workRequestsRepository;
        this.workResponsesRepository = workResponsesRepository;
    }

    @GetMapping("/customer/{customerId}")
    public EndpointResponse<Profile> getProfile(@PathVariable String customerId) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(customerId);

        if (customerProfile.isEmpty()) {
            return EndpointResponse.failed("Invalid customer!");
        }

        return EndpointResponse.passed(customerProfile.get());
    }

    @GetMapping("/customer/responses")
    public EndpointResponse<List<WorkResponse>> getWorkResponses() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String customerId = sessions.get().getUserId();
        System.out.println("CUSTOMER RESPONSES (" + customerId + ")");
        System.out.println(sessions.get().getToken());
        Optional<Profile> customerProfileQuery = this.profileGenerator.getCustomerProfile(customerId);

        if (customerProfileQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid customer!");
        }

        List<WorkResponse> workResponses = new ArrayList<>();
        List<WorkRequests> workRequestEntries = this.workRequestsRepository.findAllByCustomerId(customerId);

        for (WorkRequests workRequest : workRequestEntries) {
            Optional<WorkResponses> workResponseQuery = this.workResponsesRepository.findByWorkRequestId(workRequest.getRequestId());
            Optional<Profile> workerProfileQuery = this.profileGenerator.getWorkerProfile(workRequest.getWorkerId());

            if (workResponseQuery.isEmpty() || workerProfileQuery.isEmpty()) {
                continue;
            }

            workResponses.add(new WorkResponse(workResponseQuery.get(), customerProfileQuery.get(), workerProfileQuery.get()));
        }

        return EndpointResponse.passed(workResponses);
    }

    @GetMapping("/customer/bookings")
    public EndpointResponse<List<WorkBooking>> getBookings() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String customerId = sessions.get().getUserId();
        List<WorkBookings> workBookingEntries = this.workBookingsRepository.findAllByCustomerId(customerId);
        List<WorkBooking> workBookings = new ArrayList<>();

        for (WorkBookings workBooking : workBookingEntries) {
            if (workBooking.getStatus() > 1) {
                continue;
            }

            if (!workDetailsRepository.existsById(workBooking.getWorkDetailsId())) {
                continue;
            }

            String workerId = workBooking.getWorkerId();
            Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);
            Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(customerId);

            if (workerProfile.isEmpty() || customerProfile.isEmpty()) {
                continue;
            }

            workBookings.add(new WorkBooking(workBooking, workerProfile.get(), customerProfile.get()));
        }

        List<WorkRequests> workRequests = this.workRequestsRepository.findAllByCustomerId(customerId);

        for (WorkRequests workRequest : workRequests) {
            Optional<WorkDetails> workDetailsQuery = this.workDetailsRepository.findById(workRequest.getWorkDetailsId());
            Profile workerProfile = this.profileGenerator.getWorkerProfile(workRequest.getWorkerId()).orElse(null);
            Profile customerProfile = this.profileGenerator.getCustomerProfile(customerId).orElse(null);
            Optional<WorkResponses> workResponseQuery = this.workResponsesRepository.findByWorkRequestId(workRequest.getRequestId());

            if (workResponseQuery.isEmpty() || workDetailsQuery.isEmpty() || customerProfile == null || workerProfile == null) {
                continue;
            }

            String bookingId = workRequest.getRequestId();
            WorkResponses workResponse = workResponseQuery.get();

            WorkBooking workBooking = new WorkBooking(
                    bookingId,
                    workRequest.getWorkDetailsId(),
                    workResponse.getCost(),
                    workerProfile,
                    customerProfile,
                    workResponse.getStartDate(),
                    workResponse.getEndDate(),
                    0,
                    workRequest.getDescription()
            );

            workBookings.add(workBooking);
        }

        return EndpointResponse.passed(workBookings);
    }

    @GetMapping("/customer/requests")
    public EndpointResponse<List<WorkRequest>> getWorkRequests() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String customerId = sessions.get().getUserId();

        List<WorkRequests> workRequestEntries = this.workRequestsRepository.findAllByCustomerId(customerId);
        List<WorkRequest> workRequests = new ArrayList<>();
        for (WorkRequests workRequest : workRequestEntries) {
            if (!workDetailsRepository.existsById(workRequest.getWorkDetailsId())) {
                continue;
            }

            String workerId = workRequest.getWorkerId();
            Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);
            Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(customerId);
            if (workerProfile.isEmpty() || customerProfile.isEmpty() || workRequest.getStatus() > 1) {
                continue;
            }

            workRequests.add(new WorkRequest(workRequest, customerProfile.get(), workerProfile.get()));
        }

        return EndpointResponse.passed(workRequests);
    }

    @GetMapping("/customer/history")
    public EndpointResponse<List<WorkBooking>> getBookingHistory() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String customerId = sessions.get().getUserId();

        List<WorkBookings> workBookingEntries = this.workBookingsRepository.findAllByCustomerId(customerId);
        List<WorkBooking> bookings = new ArrayList<>();

        for (WorkBookings workBookingEntry : workBookingEntries) {
            if (workBookingEntry.getStatus() < 2) {
                continue;
            }

            if (!workDetailsRepository.existsById(workBookingEntry.getWorkDetailsId())) {
                continue;
            }

            String workerId = workBookingEntry.getWorkerId();
            Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);
            Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(customerId);

            if (workerProfile.isEmpty() || customerProfile.isEmpty()) {
                continue;
            }

            WorkBooking workBooking = new WorkBooking(workBookingEntry, workerProfile.get(), customerProfile.get());

            bookings.add(workBooking);
        }

        return EndpointResponse.passed(bookings);
    }
}
