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
    private final BidRequestsRepository bidRequestsRepository;
    private final WorkRequestsRepository workRequestsRepository;
    private final WorkBookingsRepository workBookingsRepository;
    private final UserBookingsRepository userBookingsRepository;
    private final WorkDetailsRepository workDetailsRepository;
    private final ReportsRepository reportsRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final WorkerProfileRepository workerProfileRepository;

    public Work(
            ProfileGenerator profileGenerator,
            SessionsRepository sessionsRepository,
            BidRequestsRepository bidRequestsRepository,
            WorkRequestsRepository workRequestsRepository,
            WorkBookingsRepository workBookingsRepository,
            UserBookingsRepository userBookingsRepository,
            WorkDetailsRepository workDetailsRepository,
            ReportsRepository reportsRepository,
            CustomerProfileRepository customerProfileRepository,
            WorkerProfileRepository workerProfileRepository
    ) {
        super(sessionsRepository, profileGenerator);
        this.bidRequestsRepository = bidRequestsRepository;
        this.workRequestsRepository = workRequestsRepository;
        this.workBookingsRepository = workBookingsRepository;
        this.userBookingsRepository = userBookingsRepository;
        this.workDetailsRepository = workDetailsRepository;
        this.reportsRepository = reportsRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.workerProfileRepository = workerProfileRepository;
    }

    @PostMapping("/work/create")
    public EndpointResponse<WorkRequests> createWork(@RequestBody WorkRequests workRequest) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        if (!this.customerProfileRepository.existsByUserId(workRequest.getCustomerId())) {
            return EndpointResponse.failed("Customer not found!");
        }

        if (!this.workerProfileRepository.existsByUserId(workRequest.getWorkerId())) {
            return EndpointResponse.failed("Worker not found!");
        }

        if (!this.workDetailsRepository.existsById(workRequest.getWorkDetailsId())) {
            return EndpointResponse.failed("Work details not found!");
        }

        workRequest.setRequestId(UUID.randomUUID().toString());
        this.workRequestsRepository.save(workRequest);

        return EndpointResponse.passed(workRequest);
    }

    @PostMapping("/work/bid")
    public EndpointResponse<BidRequest> createBidRequest(@RequestBody BidRequest bidRequest) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        if (!this.workRequestsRepository.existsByRequestId(bidRequest.getRequestId())) {
            return EndpointResponse.failed("Invalid work!");
        }

        BidRequests bidRequests = new BidRequests(
                bidRequest.getRequestId(),
                bidRequest.getBiddingTier()
        );
        this.bidRequestsRepository.save(bidRequests);

        return EndpointResponse.passed(bidRequest);
    }

    @PostMapping("/work/approve")
    public EndpointResponse<Boolean> approveWork(@RequestBody WorkApproval workApproval) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String requestId = workApproval.getRequestId();
        Optional<WorkRequests> workRequestQuery = this.workRequestsRepository.findByRequestId(requestId);

        if (workRequestQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid work request!");
        }

        System.out.println(workApproval.getIsApproved());

        if (workApproval.getIsApproved()) {
            String bookingId = UUID.randomUUID().toString();

            Optional<WorkRequests> workRequestsQuery = this.workRequestsRepository.findByRequestId(requestId);

            if (workRequestsQuery.isEmpty()) {
                return EndpointResponse.failed("Invalid work request!");
            }

            WorkRequests workRequests = workRequestsQuery.get();

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

            Profile workerProfile = workerProfileQuery.get();
            WorkDetails workDetails = workDetailsQuery.get();
            Optional<BidRequests> bidRequestsQuery = this.bidRequestsRepository.findByRequestId(requestId);

            int acceptedBiddingTier = bidRequestsQuery
                    .map(BidRequests::getBiddingTier)
                    .orElseGet(workRequests::getBiddingTier);

            int roundedPrice = PriceGenerator.generate(
                    workDetails.getRecommendedPrice(),
                    workerProfile.getWorkerProfile().getYearsOfExperience(),
                    workerProfile.getRegistrationDate(),
                    acceptedBiddingTier
            );

            UserBookings userBookingEntry = new UserBookings(
                    bookingId,
                    workRequests.getCustomerId(),
                    1
            );

            WorkBookings workBookingEntry = new WorkBookings(
                    bookingId,
                    workRequests.getWorkDetailsId(),
                    workRequests.getWorkerId(),
                    acceptedBiddingTier,
                    roundedPrice
            );

            this.userBookingsRepository.save(userBookingEntry);
            this.workBookingsRepository.save(workBookingEntry);
        }

        this.workRequestsRepository.deleteByRequestId(requestId);
        this.bidRequestsRepository.deleteByRequestId(requestId);

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

        return EndpointResponse.passed(new WorkBooking(
                workBookingsQuery.get(),
                workerProfile.get()
        ));
    }

    @PostMapping("/work/complete")
    public EndpointResponse<Boolean> completeWork(@RequestBody WorkCompletion workCompletion) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String bookingId = workCompletion.getBookingId();
        Optional<UserBookings> userBookingEntry = this.userBookingsRepository.findByBookingId(bookingId);

        if (userBookingEntry.isEmpty()) {
            return EndpointResponse.failed("Invalid booking!");
        }

        UserBookings userBooking = userBookingEntry.get();

        if (workCompletion.getIsReported()) {
            Reports report = new Reports(
                    UUID.randomUUID().toString(),
                    bookingId,
                    workCompletion.getReportReason()
            );

            this.reportsRepository.save(report);

            userBooking.setStatus(2);
            this.userBookingsRepository.save(userBooking);
        } else {
            userBooking.setStatus(3);
            this.userBookingsRepository.save(userBooking);
        }

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
