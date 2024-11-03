package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.AdministratorAction;
import com.tinkerly.tinkerly.payloads.Report;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
public class Administrator extends SessionController {
    private final ReportsRepository reportsRepository;
    private final AdministratorProfileRepository administratorProfileRepository;
    private final WorkBookingsRepository workBookingsRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final UserBookingsRepository userBookingsRepository;

    public Administrator(
            SessionsRepository sessionsRepository,
            ReportsRepository reportsRepository,
            AdministratorProfileRepository administratorProfileRepository,
            WorkBookingsRepository workBookingsRepository,
            WorkerProfileRepository workerProfileRepository,
            UserBookingsRepository userBookingsRepository

    ) {
        super(sessionsRepository);
        this.reportsRepository = reportsRepository;
        this.administratorProfileRepository = administratorProfileRepository;
        this.workBookingsRepository = workBookingsRepository;
        this.workerProfileRepository = workerProfileRepository;
        this.userBookingsRepository = userBookingsRepository;
    }

    @GetMapping("/admin/reports/{page}")
    public EndpointResponse<List<Reports> > getReports(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return EndpointResponse.passed(this.reportsRepository.findAll(pageable));
    }

    @PostMapping ("/admin/act")
    public EndpointResponse<Boolean> takeAction(@RequestBody AdministratorAction adminAction) {
        Optional<Sessions> sessionQuery = this.getSession();
        if (sessionQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid session!");
        }

        Optional<AdministratorProfiles> administratorProfileQuery = this.administratorProfileRepository.findByUserId(
                sessionQuery.get().getUserId()
        );

        if (administratorProfileQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid administrator!");
        }

        Optional<Reports> reportQuery = this.reportsRepository.findById(adminAction.getReportId());

        if (reportQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid report!");
        }

        Reports reports = reportQuery.get();
        String bookingId = reports.getBookingId();

        Optional<WorkBookings> workBookingsQuery = this.workBookingsRepository.findByBookingId(bookingId);

        if (workBookingsQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid booking!");
        }

        WorkBookings workBookings = workBookingsQuery.get();

        Optional<WorkerProfiles> workerProfileQuery = this.workerProfileRepository.findByUserId(
                workBookings.getWorkerId()
        );

        if (workerProfileQuery.isEmpty()) {
            return EndpointResponse.failed("Invalid worker!");
        }

        AdministratorProfiles administratorProfile = administratorProfileQuery.get();
        WorkerProfiles workerProfile = workerProfileQuery.get();

        switch (adminAction.getActionType()) {
            case 0: {
                if (!administratorProfile.isBanAuthority()) {
                    return EndpointResponse.failed("Insufficient permissions!");
                }

                workerProfile.setBanned(true);
                workerProfile.setSuspension(null);

                break;
            }
            case 1: {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 90);

                workerProfile.setSuspension(calendar.getTime());

                break;
            }
            default: {
                return EndpointResponse.failed("Invalid action!");
            }
        }

        this.workerProfileRepository.save(workerProfile);
        this.workBookingsRepository.deleteByBookingId(bookingId);
        this.userBookingsRepository.deleteByBookingId(bookingId);

        return EndpointResponse.passed(true);
    }
}
