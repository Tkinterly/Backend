package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.PriceGenerator;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestController
@CrossOrigin
public class Work extends SessionController {
    private final WorkRequestsRepository workRequestsRepository;
    private final WorkBookingsRepository workBookingsRepository;
    private final WorkDetailsRepository workDetailsRepository;
    private final ReportsRepository reportsRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final WorkResponsesRepository workResponsesRepository;
    private final WorkerReviewsRepository workerReviewsRepository;

    public Work(
            ProfileGenerator profileGenerator,
            SessionsRepository sessionsRepository,
            WorkRequestsRepository workRequestsRepository,
            WorkBookingsRepository workBookingsRepository,
            WorkDetailsRepository workDetailsRepository,
            ReportsRepository reportsRepository,
            CustomerProfileRepository customerProfileRepository,
            WorkerProfileRepository workerProfileRepository,
            WorkResponsesRepository workResponsesRepository,
            WorkerReviewsRepository workerReviewsRepository
    ) {
        super(sessionsRepository, profileGenerator);
        this.workRequestsRepository = workRequestsRepository;
        this.workBookingsRepository = workBookingsRepository;
        this.workDetailsRepository = workDetailsRepository;
        this.reportsRepository = reportsRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.workerProfileRepository = workerProfileRepository;
        this.workResponsesRepository = workResponsesRepository;
        this.workerReviewsRepository = workerReviewsRepository;
    }

    @PostMapping("/work/create/request")
    public EndpointResponse<WorkRequests> createWorkRequest(@RequestBody WorkRequests workRequest) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        if (!this.customerProfileRepository.existsByUserId(workRequest.getCustomerId())) {
            return EndpointResponse.failed("Customer not found!");
        }

        if (!this.workerProfileRepository.existsByUserId(workRequest.getWorkerId())) {
            return EndpointResponse.failed("Worker not found!");
        }

        Optional<WorkDetails> workDetailsQuery = workDetailsRepository.findById(workRequest.getWorkDetailsId());

        if (workDetailsQuery.isEmpty()) {
            return EndpointResponse.failed("Work details not found!");
        }

        // Three cases
        boolean endsDuringExistingWork = false;
        boolean startsDuringExistingWork = false;
        boolean existsBetweenExistingWork = false;

        List<WorkBookings> workBookingEntries = this.workBookingsRepository.findAllByWorkerId(workRequest.getWorkerId());

        for (WorkBookings workBooking : workBookingEntries) {

            if (workBooking.getStatus() != 1) {
                continue;
            }

            Instant bookingStartInstant = workBooking.getStartDate().toInstant();
            Instant bookingEndInstant = workBooking.getEndDate().toInstant();



        }

        workRequest.setRequestId(UUID.randomUUID().toString());
        this.workRequestsRepository.save(workRequest);

        return EndpointResponse.passed(workRequest);
    }

    @PostMapping("/work/create/response")
    public EndpointResponse<WorkResponse> createWorkResponse(@RequestBody WorkResponse workResponse) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        Optional<WorkRequests> workRequestQuery = this.workRequestsRepository.findByRequestId(workResponse.getWorkRequestId());

        if (workRequestQuery.isEmpty()) {
            return EndpointResponse.failed("Work request not found!");
        }

        WorkRequests workRequest = workRequestQuery.get();

        if (!this.customerProfileRepository.existsByUserId(workRequest.getCustomerId())) {
            return EndpointResponse.failed("Customer not found!");
        }

        if (!this.workerProfileRepository.existsByUserId(workRequest.getWorkerId())) {
            return EndpointResponse.failed("Worker not found!");
        }

        WorkResponses workResponseEntry = new WorkResponses(workResponse);
        this.workResponsesRepository.save(workResponseEntry);

        return EndpointResponse.passed(workResponse);
    }

    @PostMapping("/work/approve")
    public EndpointResponse<Boolean> approveWork(@RequestBody WorkApproval workApproval) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String requestId = workApproval.getRequestId();
        Optional<WorkRequests> workRequestQuery = this.workRequestsRepository.findByRequestId(requestId);
        Optional<WorkResponses> workResponseQuery = this.workResponsesRepository.findByWorkRequestId(requestId);

        if (workRequestQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid work request!");
        }

        WorkRequests workRequests = workRequestQuery.get();
        if (workResponseQuery.isEmpty()) {
            workRequests.setStatus(2);
            this.workRequestsRepository.save(workRequests);
            return EndpointResponse.passed(true);
        }

        WorkResponses workResponses = workResponseQuery.get();
        workRequests.setStatus(1);
        this.workRequestsRepository.save(workRequests);

        if (workApproval.getIsApproved()) {
            String bookingId = UUID.randomUUID().toString();

            Optional<Profile> workerProfileQuery = this.profileGenerator
                    .getWorkerProfile(workRequests.getWorkerId());

            if (workerProfileQuery.isEmpty()) {
                return EndpointResponse.failed("Invalid worker!");
            }

            Optional<WorkDetails> workDetailsQuery = this.workDetailsRepository.findById(
                    workRequests.getWorkDetailsId()
            );

            if (workDetailsQuery.isEmpty()) {
                return EndpointResponse.failed("Invalid work details!");
            }

            WorkBookings workBookingEntry = new WorkBookings(
                    bookingId,
                    workRequests.getWorkDetailsId(),
                    workRequests.getWorkerId(),
                    workRequests.getCustomerId(),
                    workResponses.getCost(),
                    workResponses.getStartDate(),
                    workResponses.getEndDate(),
                    1,
                    workRequests.getDescription()
            );

            workResponses.setStatus(1);
            this.workBookingsRepository.save(workBookingEntry);
        } else {
            workResponses.setStatus(2);
            this.workResponsesRepository.save(workResponses);
        }

        return EndpointResponse.passed(true);
    }

    @GetMapping("/work/{bookingId}")
    public EndpointResponse<WorkBooking> getWork(@PathVariable String bookingId) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        Optional<WorkBookings> workBookingsQuery = this.workBookingsRepository.findByBookingId(bookingId);
        if (workBookingsQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid booking!");
        }

        Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(
                workBookingsQuery.get().getWorkerId()
        );

        if (workerProfile.isEmpty()) {
            return EndpointResponse.failed("Invalid worker!");
        }

        Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(
                workBookingsQuery.get().getCustomerId()
        );

        if (customerProfile.isEmpty()) {
            return EndpointResponse.failed("Invalid customer!");
        }

        return EndpointResponse.passed(new WorkBooking(
                workBookingsQuery.get(),
                workerProfile.get(),
                customerProfile.get()
        ));
    }

    @PostMapping("/work/complete")
    public EndpointResponse<Boolean> completeWork(@RequestBody WorkCompletion workCompletion) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String bookingId = workCompletion.getBookingId();
        Optional<WorkBookings> workBookingEntry = this.workBookingsRepository.findByBookingId(bookingId);

        if (workBookingEntry.isEmpty()) {
            return EndpointResponse.failed("Invalid booking!");
        }

        WorkBookings workBooking = workBookingEntry.get();

        Optional<WorkDetails> workDetailsEntry = this.workDetailsRepository.findById(workBooking.getWorkDetailsId());
        if (workDetailsEntry.isEmpty()) {
            return EndpointResponse.failed("Invalid work details!");
        }

        if (workCompletion.getIsReported()) {
            Reports report = new Reports(
                    UUID.randomUUID().toString(),
                    bookingId,
                    workCompletion.getReview()
            );

            this.reportsRepository.save(report);

            workBooking.setStatus(2);
        } else {
            WorkerReviews workerReview = new WorkerReviews(
                    workBooking.getWorkerId(),
                    bookingId,
                    workDetailsEntry.get().getDomain(),
                    workCompletion.getReview()
            );

            this.workerReviewsRepository.save(workerReview);

            workBooking.setStatus(3);
        }

        this.workBookingsRepository.save(workBooking);

        return EndpointResponse.passed(true);
    }

    @GetMapping("/work/details")
    public EndpointResponse<List<WorkDetail>> getWorkDetails() {
        List<WorkDetail> workDetails = new ArrayList<>();

        for (WorkDetails workDetail : this.workDetailsRepository.findAll()) {
            workDetails.add(new WorkDetail(workDetail));
        }

        return EndpointResponse.passed(workDetails);
    }
}
