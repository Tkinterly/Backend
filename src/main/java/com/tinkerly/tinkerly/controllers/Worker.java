package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@RestController
@CrossOrigin
public class Worker extends SessionController {
    private final WorkRequestsRepository workRequestsRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final WorkerDomainsRepository workerDomainsRepository;
    private final WorkDetailsRepository workDetailsRepository;
    private final WorkerSkillsRepository workerSkillsRepository;
    private final WorkBookingsRepository workBookingsRepository;
    private final WorkerReviewsRepository workerReviewsRepository;
    private final WorkResponsesRepository workResponsesRepository;

    public Worker(
            SessionsRepository sessionsRepository,
            ProfileGenerator profileGenerator,
            WorkRequestsRepository workRequestsRepository,
            WorkerProfileRepository workerProfileRepository,
            WorkerDomainsRepository workerDomainsRepository,
            WorkDetailsRepository workDetailsRepository,
            WorkerSkillsRepository workerSkillsRepository,
            WorkBookingsRepository workBookingsRepository,
            WorkerReviewsRepository workerReviewsRepository,
            WorkResponsesRepository workResponsesRepository
    ) {
        super(sessionsRepository, profileGenerator);
        this.workRequestsRepository = workRequestsRepository;
        this.workerProfileRepository  = workerProfileRepository;
        this.workerDomainsRepository = workerDomainsRepository;
        this.workDetailsRepository = workDetailsRepository;
        this.workerSkillsRepository  = workerSkillsRepository;
        this.workBookingsRepository = workBookingsRepository;
        this.workerReviewsRepository = workerReviewsRepository;
        this.workResponsesRepository = workResponsesRepository;
    }

    @GetMapping("/worker/{workerId}")
    public EndpointResponse<Profile> getProfile(@PathVariable String workerId) {
        if (!this.isValidSession()) {
            // Invalid session
            return EndpointResponse.failed("Invalid session!");
        }

        Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);

        if (workerProfile.isEmpty()) {
            return EndpointResponse.failed("Invalid worker!");
        }

        return EndpointResponse.passed(workerProfile.get());
    }

    @PostMapping("/worker/find")
    public EndpointResponse<List<WorkerProposal>> findWorkers(@RequestBody WorkerFindRequest workerFindRequest) {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String workDetailsId = workerFindRequest.getWorkDetailsId();
        Optional<WorkDetails> workDetailsQuery = this.workDetailsRepository.findById(workDetailsId);
        if (workDetailsQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid work details!");
        }

        WorkDetails workDetails = workDetailsQuery.get();
        List<WorkerSkills> workerSkillsQuery = this.workerSkillsRepository.findAllBySkill(workDetailsId);
        List<WorkerProposal> workerProposals = new ArrayList<>();

        for (WorkerSkills workerSkills : workerSkillsQuery) {
            String workerId = workerSkills.getUserId();
            Optional<Profile> workerProfileQuery = this.profileGenerator.getWorkerProfile(workerId);

            if (workerProfileQuery.isEmpty()) {
                continue;
            }

            Profile workerProfile = workerProfileQuery.get();

            int workerExperience = workerProfile.getWorkerProfile().getYearsOfExperience();
            int platformPresence = Period.between(
                    workerProfile.getRegistrationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    LocalDate.now()
            ).getMonths();
            double biddingPrice = workDetails.getRecommendedPrice();
            double experienceFactor = 0.2 * (1 + Math.log10(1 + workerExperience));
            double estimatedPrice = 0.9 * biddingPrice * (1 + experienceFactor * Math.log10(1 + platformPresence));

            List<Integer> recommendedPrices = new ArrayList<>();

            for (int i = 0; i < 5; ++i) {
                recommendedPrices.add((int) (10 * Math.round(estimatedPrice * (0.95 + 0.025 * i) / 10)));
            }

            workerProposals.add(new WorkerProposal(workerProfile, recommendedPrices));
        }

        return EndpointResponse.passed(workerProposals);
    }

    @GetMapping("/worker/requests")
    public EndpointResponse<List<WorkRequest>> getRequests() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String workerId = sessions.get().getUserId();
        if (!this.workerProfileRepository.existsByUserId(workerId)) {
            return EndpointResponse.failed("Invalid worker!");
        }

        List<WorkRequests> workRequestsQuery = this.workRequestsRepository.findAllByWorkerId(workerId);
        List<WorkRequest> workRequests = new ArrayList<>();

        for (WorkRequests workRequest : workRequestsQuery) {
            Optional<Profile> customer = this.profileGenerator.getCustomerProfile(workRequest.getCustomerId());
            Optional<Profile> worker = this.profileGenerator.getWorkerProfile(workRequest.getWorkerId());
            if (customer.isEmpty() || worker.isEmpty() || workRequest.getStatus() > 1) {
                continue;
            }

            workRequests.add(new WorkRequest(workRequest, customer.get(), worker.get()));
        }

        return EndpointResponse.passed(workRequests);
    }

    @GetMapping("/worker/responses")
    public EndpointResponse<List<WorkResponse>> getWorkResponses() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String workerId = sessions.get().getUserId();
        Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);

        if (workerProfile.isEmpty()) {
            return EndpointResponse.failed("Invalid worker!");
        }

        List<WorkResponse> workResponses = new ArrayList<>();
        List<WorkRequests> workRequestEntries = this.workRequestsRepository.findAllByWorkerId(workerId);

        for (WorkRequests workRequest : workRequestEntries) {
            Optional<WorkResponses> workResponseQuery = this.workResponsesRepository.findByWorkRequestId(workRequest.getRequestId());
            Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(workRequest.getCustomerId());
            if (workResponseQuery.isEmpty() || customerProfile.isEmpty()) {
                continue;
            }

            WorkRequest workRequestDTO = new WorkRequest(workRequest, workerProfile.get(), customerProfile.get());

            workResponses.add(new WorkResponse(workResponseQuery.get(), workRequestDTO));
        }

        return EndpointResponse.passed(workResponses);
    }

    @GetMapping("/worker/bookings")
    public EndpointResponse<List<WorkBooking>> getBookings() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String workerId = sessions.get().getUserId();
        List<WorkBookings> workBookingEntries = this.workBookingsRepository.findAllByWorkerId(workerId);
        List<WorkBooking> workBookings = new ArrayList<>();

        for (WorkBookings workBookingEntry : workBookingEntries) {
            Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);
            Optional<Profile> customerProfile = this.profileGenerator.getCustomerProfile(workBookingEntry.getCustomerId());

            if (workerProfile.isEmpty() || customerProfile.isEmpty()) {
                continue;
            }

            WorkBooking workBooking = new WorkBooking(workBookingEntry, workerProfile.get(), customerProfile.get());
            workBookings.add(workBooking);
        }

        return EndpointResponse.passed(workBookings);
    }

    @GetMapping("/worker/reviews")
    public EndpointResponse<List<WorkerReviews>> getReviews() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String workerId = sessions.get().getUserId();
        List<WorkerReviews> workerReviewEntries = this.workerReviewsRepository.findByWorkerId(workerId);

        return EndpointResponse.passed(workerReviewEntries);
    }

    @GetMapping("/workers")
    public EndpointResponse<List<List<Profile>>> getWorkers() {
        if (!this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }
        List<List<Profile>> workersByCategories = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            workersByCategories.add(new ArrayList<>());
        }

        for (WorkerProfiles workerProfileQuery : this.workerProfileRepository.findAll()) {
            String workerId = workerProfileQuery.getUserId();
            Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);

            if (workerProfile.isEmpty()) {
                continue;
            }

            List<WorkerDomains> workerDomainsQuery = this.workerDomainsRepository.findAllByUserId(workerId);

            for (WorkerDomains workerDomainQuery : workerDomainsQuery) {
                workersByCategories.get(workerDomainQuery.getDomain()).add(workerProfile.get());
            }
        }

        return EndpointResponse.passed(workersByCategories);
    }

    @GetMapping("/worker/history")
    public EndpointResponse<List<WorkBooking>> getBookingHistory() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty() || !this.isValidSession()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String workerId = sessions.get().getUserId();

        List<WorkBookings> workBookingEntries = this.workBookingsRepository.findAllByWorkerId(workerId);
        List<WorkBooking> bookings = new ArrayList<>();

        for (WorkBookings workBookingEntry : workBookingEntries) {
            if (workBookingEntry.getStatus() < 2) {
                continue;
            }

            if (!workDetailsRepository.existsById(workBookingEntry.getWorkDetailsId())) {
                continue;
            }

            String customerId = workBookingEntry.getCustomerId();
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
