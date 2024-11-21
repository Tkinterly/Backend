package com.tinkerly.tinkerly.controllers;

import com.tinkerly.tinkerly.components.EndpointResponse;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.entities.*;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class Administrator extends SessionController {

    private final ReportsRepository reportsRepository;
    private final AdministratorProfileRepository administratorProfileRepository;
    private final WorkBookingsRepository workBookingsRepository;
    private final WorkerProfileRepository workerProfileRepository;
    private final UserBookingsRepository userBookingsRepository;

    public Administrator(
            ProfileGenerator profileGenerator,
            SessionsRepository sessionsRepository,
            ReportsRepository reportsRepository,
            AdministratorProfileRepository administratorProfileRepository,
            WorkBookingsRepository workBookingsRepository,
            WorkerProfileRepository workerProfileRepository,
            UserBookingsRepository userBookingsRepository

    ) {
        super(sessionsRepository, profileGenerator);
        this.reportsRepository = reportsRepository;
        this.administratorProfileRepository = administratorProfileRepository;
        this.workBookingsRepository = workBookingsRepository;
        this.workerProfileRepository = workerProfileRepository;
        this.userBookingsRepository = userBookingsRepository;
    }

    @GetMapping("/admin/reports/{page}")
    public EndpointResponse<List<Report>> getReports(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 10);

        List<Reports> reportsQuery = this.reportsRepository.findAll(pageable);

        List<Report> reports = new ArrayList<>();
        for (Reports report : reportsQuery) {
            Optional<WorkBookings> workBookingsQuery = this.workBookingsRepository.findByBookingId(report.getBookingId());
            if (workBookingsQuery.isEmpty()) {
                continue;
            }

            WorkBookings workBookingEntry = workBookingsQuery.get();

            String workerId = workBookingEntry.getWorkerId();
            Optional<Profile> workerProfile = this.profileGenerator.getWorkerProfile(workerId);

            if (workerProfile.isEmpty()) {
                continue;
            }

            reports.add(new Report(
                    report,
                    new WorkBooking(workBookingEntry, workerProfile.get())
            ));
        }

        return EndpointResponse.passed(reports);
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
            case 2: {
                break;
            }
            default: {
                return EndpointResponse.failed("Invalid action!");
            }
        }

        this.workerProfileRepository.save(workerProfile);
        this.workBookingsRepository.deleteByBookingId(bookingId);
        this.userBookingsRepository.deleteByBookingId(bookingId);
        this.reportsRepository.deleteByReportId(reports.getReportId());

        return EndpointResponse.passed(true);
    }
}
