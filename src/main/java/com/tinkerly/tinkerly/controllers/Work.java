package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
public class Work extends SessionController {
    private final ProfileGenerator profileGenerator;

    private final BidRequestsRepository bidRequestsRepository;
    private final WorkRequestsRepository workRequestsRepository;
    private final WorkBookingsRepository workBookingsRepository;
    private final UserBookingsRepository userBookingsRepository;
    private final WorkDetailsRepository workDetailsRepository;
    private final ReportsRepository reportsRepository;

    public Work(
            ProfileGenerator profileGenerator,
            SessionsRepository sessionsRepository,
            BidRequestsRepository bidRequestsRepository,
            WorkRequestsRepository workRequestsRepository,
            WorkBookingsRepository workBookingsRepository,
            UserBookingsRepository userBookingsRepository,
            WorkDetailsRepository workDetailsRepository,
            ReportsRepository reportsRepository
    ) {
        super(sessionsRepository);
        this.profileGenerator = profileGenerator;
        this.bidRequestsRepository = bidRequestsRepository;
        this.workRequestsRepository = workRequestsRepository;
        this.workBookingsRepository = workBookingsRepository;
        this.userBookingsRepository = userBookingsRepository;
        this.workDetailsRepository = workDetailsRepository;
        this.reportsRepository = reportsRepository;
    }

    @PostMapping("/work/create")
    public EndpointResponse<WorkRequest> createWork(@RequestBody WorkRequest workRequest) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        workRequest.setWorkDetailsId(UUID.randomUUID().toString());

        WorkRequests workRequests = new WorkRequests(workRequest);
        this.workRequestsRepository.save(workRequests);

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

        if (workApproval.isApproved()) {
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

            int workerExperience = workerProfile.getWorkerProfile().getYearsOfExperience();
            int platformPresence = Period.between(
                    workerProfile.getRegistrationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    LocalDate.now()
            ).getMonths();
            double biddingPrice = workDetails.getRecommendedPrice() * (1 - (acceptedBiddingTier - 2)  * 0.025);
            double experienceFactor = 0.2 * (1 + Math.log10(1 + workerExperience));
            double estimatedPrice = 0.9 * biddingPrice * (1 + experienceFactor * Math.log10(1 + platformPresence));
            int roundedPrice = (int) (Math.round(estimatedPrice / 10) * 10);

            UserBookings userBookingEntry = new UserBookings(
                    bookingId,
                    workRequests.getCustomerId(),
                    0
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
    public EndpointResponse<String> completeWork(@RequestBody WorkCompletion workCompletion) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String bookingId = workCompletion.getBookingId();

        if (workCompletion.isReported()) {
            Reports report = new Reports(
                    UUID.randomUUID().toString(),
                    bookingId,
                    workCompletion.getReportReason()
            );

            this.reportsRepository.save(report);
        } else {
            this.workBookingsRepository.deleteByBookingId(bookingId);
            this.userBookingsRepository.deleteByBookingId(bookingId);
        }

        return EndpointResponse.failed("");
    }
}
