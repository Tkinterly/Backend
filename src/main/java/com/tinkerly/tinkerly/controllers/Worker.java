package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class Worker extends SessionController {

    private final ProfileGenerator profileGenerator;

    private final WorkRequestsRepository workRequestsRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final BidRequestsRepository bidRequestsRepository;
    private final WorkerDomainsRepository workerDomainsRepository;

    public Worker(
            SessionsRepository sessionsRepository,
            ProfileGenerator profileGenerator,
            WorkRequestsRepository workRequestsRepository,
            WorkerProfileRepository workerProfileRepository,
            BidRequestsRepository bidRequestsRepository,
            WorkerDomainsRepository workerDomainsRepository
    ) {
        super(sessionsRepository);
        this.profileGenerator = profileGenerator;
        this.workRequestsRepository = workRequestsRepository;
        this.workerProfileRepository  = workerProfileRepository;
        this.bidRequestsRepository = bidRequestsRepository;
        this.workerDomainsRepository = workerDomainsRepository;
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

        // matching work domain and work type
        // similar platform experience
        // price evaluation
        return EndpointResponse.failed("");
    }

    @GetMapping("/worker/requests")
    public EndpointResponse<ListingsResponse<WorkRequest>> getRequests() {
        Optional<Sessions> sessions = this.getSession();
        if (sessions.isEmpty()) {
            return EndpointResponse.failed("Invalid session!");
        }

        String workerId = sessions.get().getUserId();

        if (!this.workerProfileRepository.existsByUserId(workerId)) {
            return EndpointResponse.failed("Invalid worker!");
        }

        List<WorkRequests> workRequestsQuery = this.workRequestsRepository.findAllByWorkerId(workerId);
        List<WorkRequest> workRequests = new ArrayList<>();
        List<BidRequest> bidRequests = new ArrayList<>();

        for (WorkRequests workRequest : workRequestsQuery) {
            workRequests.add(new WorkRequest(workRequest));

            Optional<BidRequests> bidRequest = this.bidRequestsRepository.findByRequestId(workRequest.getRequestId());

            if (bidRequest.isEmpty()) {
                continue;
            }

            bidRequests.add(new BidRequest(bidRequest.get()));
        }

        return EndpointResponse.passed(new ListingsResponse<>(workRequests, bidRequests));
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
}
