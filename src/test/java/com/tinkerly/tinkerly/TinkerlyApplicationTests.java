package com.tinkerly.tinkerly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinkerly.tinkerly.components.ProfileGenerator;
import com.tinkerly.tinkerly.controllers.Worker;
import com.tinkerly.tinkerly.entities.WorkDetails;
import com.tinkerly.tinkerly.entities.WorkRequests;
import com.tinkerly.tinkerly.payloads.*;
import com.tinkerly.tinkerly.repositories.WorkDetailsRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = TinkerlyApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class TinkerlyApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Autowired
	public WorkDetailsRepository workDetailsRepository;

	@Autowired
	public ProfileGenerator profileGenerator;

	private UserDetail createTestingUserDetail() {
		String name = UUID.randomUUID().toString();
		String email = UUID.randomUUID().toString();
		String phone = UUID.randomUUID().toString();
		int age = (int) Math.round(Math.random() * 30) + 20;
		String address = UUID.randomUUID().toString();
		int nic = (int) (Math.round(Math.random() * 1e7) + 1e6);

		UserDetail userDetail = new UserDetail();
		userDetail.setName(name);
		userDetail.setEmail(email);
		userDetail.setPhone(phone);
		userDetail.setAge(age);
		userDetail.setAddress(address);
		userDetail.setNic(nic);
		return userDetail;
	}

	private Profile createTestingWorker() {
		int randomDomain = (int) Math.floor(Math.random() * 5);
		List<WorkDetails> workDetailsList = this.workDetailsRepository.findAllByDomain(randomDomain);
		List<String> selectedSkills = new ArrayList<>();
		List<Integer> selectedDomains = new ArrayList<>();
		List<String> selectedEducation = new ArrayList<>();
		int randomSkillAmount = (int) Math.floor(Math.random() * 3) + 1;
		for (int i = 0; i < randomSkillAmount; ++i) {
			WorkDetails workDetails = workDetailsList.get((int) Math.floor(Math.random() * workDetailsList.size()));
			selectedSkills.add(workDetails.getId());
			selectedDomains.add(workDetails.getDomain());
			selectedEducation.add(UUID.randomUUID().toString());
		}

		WorkerProfile workerProfile = new WorkerProfile(
				(int) Math.round(Math.random() * 10),
				selectedDomains,
				selectedEducation,
				selectedSkills
		);

		Profile profile = new Profile();
		profile.setUserDetails(this.createTestingUserDetail());
		profile.setWorkerProfile(workerProfile);

		return profile;
	}

	private Profile createTestingCustomer() {
		int randomBookingSlots = (int) Math.floor(Math.random() * 5);
		CustomerProfile customer = new CustomerProfile(randomBookingSlots);
		Profile profile = new Profile();
		profile.setUserDetails(this.createTestingUserDetail());
		profile.setCustomerProfile(customer);
		return profile;
	}

	private Profile getTestingAdministrator() {
		return this.profileGenerator.getAdministratorProfile("63e5460c-38a0-4c1c-abac-595b36945d7a")
				.orElse(null);
	}

	private String getTestingAdministratorToken() throws Exception {
		String administratorId = "63e5460c-38a0-4c1c-abac-595b36945d7a";
		CredentialsLogin credentialsLogin = new CredentialsLogin();
		credentialsLogin.setUsername("testadmin");
		credentialsLogin.setPassword("testadmin123");

		// Test Case 2: Credentials Login Customer
		MvcResult credentialLoginResult = this.mvc.perform(
				MockMvcRequestBuilders.post("/authenticate/credentials")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(credentialsLogin))
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.data.profile.userId").value(administratorId)
		).andReturn();

		JSONObject credentialsLoginObject = new JSONObject(credentialLoginResult.getResponse().getContentAsString());
		return credentialsLoginObject.getJSONObject("data").getString("token");
	}

	private String registerCustomerAndGetToken() throws Exception {
		String testingUsername = UUID.randomUUID().toString();
		String testingPassword = UUID.randomUUID().toString();
		Profile customerProfile = this.createTestingCustomer();

		RegistrationRequest registrationRequest = new RegistrationRequest();
		registrationRequest.setUsername(testingUsername);
		registrationRequest.setPassword(testingPassword);
		registrationRequest.setUserProfile(customerProfile);

		// Test Case 1: Register Customer
		MvcResult registerResult = this.mvc.perform(
				MockMvcRequestBuilders.post("/register")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(registrationRequest))
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		).andReturn();

		JSONObject registerObject = new JSONObject(registerResult.getResponse().getContentAsString());
		String userId = registerObject.getString("data");

		CredentialsLogin credentialsLogin = new CredentialsLogin();
		credentialsLogin.setUsername(testingUsername);
		credentialsLogin.setPassword(testingPassword);

		// Test Case 2: Credentials Login Customer
		MvcResult credentialLoginResult = this.mvc.perform(
				MockMvcRequestBuilders.post("/authenticate/credentials")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(credentialsLogin))
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.data.profile.userId").value(userId)
		).andReturn();

		JSONObject credentialsLoginObject = new JSONObject(credentialLoginResult.getResponse().getContentAsString());
		return credentialsLoginObject.getJSONObject("data").getString("token");
	}

	private String registerWorkerAndGetToken() throws Exception {
		String testingUsername = UUID.randomUUID().toString();
		String testingPassword = UUID.randomUUID().toString();
		Profile workerProfile = this.createTestingWorker();

		RegistrationRequest registrationRequest = new RegistrationRequest();
		registrationRequest.setUsername(testingUsername);
		registrationRequest.setPassword(testingPassword);
		registrationRequest.setUserProfile(workerProfile);

		// Test Case 1: Register Worker
		MvcResult registerResult = this.mvc.perform(
				MockMvcRequestBuilders.post("/register")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(registrationRequest))
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		).andReturn();

		JSONObject registerObject = new JSONObject(registerResult.getResponse().getContentAsString());
		String userId = registerObject.getString("data");

		CredentialsLogin credentialsLogin = new CredentialsLogin();
		credentialsLogin.setUsername(testingUsername);
		credentialsLogin.setPassword(testingPassword);

		// Test Case 2: Credentials Login Worker
		MvcResult credentialLoginResult = this.mvc.perform(
				MockMvcRequestBuilders.post("/authenticate/credentials")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(credentialsLogin))
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data.profile.userId").value(userId)
		).andReturn();

		JSONObject credentialsLoginObject = new JSONObject(credentialLoginResult.getResponse().getContentAsString());
		return credentialsLoginObject.getJSONObject("data").getString("token");
	}

	private JSONObject performTokenLogin(String token) throws Exception {
		TokenLogin tokenLogin = new TokenLogin();
		tokenLogin.setToken(token);

		return new JSONObject(this.mvc.perform(
				MockMvcRequestBuilders.post("/authenticate/token")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(tokenLogin))
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data.token").value(token)
		).andReturn().getResponse().getContentAsString());
	}

	private ResultActions getCustomerBookings(String token) throws Exception {
		return this.mvc.perform(
				MockMvcRequestBuilders.get("/customer/bookings")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.header("Authorization", "Bearer " + token)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);
	}

	private ResultActions getWorkerBookings(String token) throws Exception {
		return this.mvc.perform(
				MockMvcRequestBuilders.get("/worker/bookings")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.header("Authorization", "Bearer " + token)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);
	}

	private ResultActions getWorkerRequests(String token) throws Exception {
		return this.mvc.perform(
				MockMvcRequestBuilders.get("/worker/requests")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.header("Authorization", "Bearer " + token)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);
	}

	public BookingRequestData createBookingRequest() throws Exception {
		// Test Case 1: Register and Login a new Worker
		String workerToken = this.registerWorkerAndGetToken();
		JSONObject workerLoginData = this.performTokenLogin(workerToken);

		String workerId = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");;

		JSONArray workerSkills = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getJSONObject("workerProfile")
				.getJSONArray("workerSkills");

		// Test Case 2: Register and Login a new Customer
		String customerToken = this.registerCustomerAndGetToken();
		JSONObject customerLoginData = this.performTokenLogin(customerToken);

		String customerId = customerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");

		String workDetailsId = workerSkills.getString(0);

		WorkRequests workRequest = WorkRequests.create();
		workRequest.setBiddingTier(0);
		workRequest.setWorkDetailsId(workDetailsId);
		workRequest.setCustomerId(customerId);
		workRequest.setWorkerId(workerId);

		// Test Case 3: Create booking request
		MvcResult bookingRequestResult = this.mvc.perform(
				MockMvcRequestBuilders.post("/work/create")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workRequest))
						.header("Authorization", "Bearer " + customerToken)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data.customerId").value(workRequest.getCustomerId()),
				MockMvcResultMatchers.jsonPath("$.data.workerId").value(workRequest.getWorkerId()),
				MockMvcResultMatchers.jsonPath("$.data.workDetailsId").value(workDetailsId),
				MockMvcResultMatchers.jsonPath("$.data.biddingTier").value(workRequest.getBiddingTier())
		).andReturn();

		JSONObject bookingRequestObject = new JSONObject(bookingRequestResult.getResponse().getContentAsString());
		String requestId = bookingRequestObject.getJSONObject("data").getString("requestId");

		// Test Case 4: Verify creation of Customer Booking with Pending Status
		this.getCustomerBookings(customerToken)
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings").isNotEmpty(),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].bookingId").value(requestId),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].status").value(0)
				);

		// Test Case 5: Verify creation of Worker Request
		this.getWorkerRequests(workerToken)
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings").isNotEmpty(),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].requestId").value(requestId),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].workDetailsId").value(workDetailsId)
				);

		BookingRequestData bookingRequestData = new BookingRequestData();
		bookingRequestData.setCustomerToken(customerToken);
		bookingRequestData.setWorkerToken(workerToken);
		bookingRequestData.setWorkDetailsId(workDetailsId);
		bookingRequestData.setRequestId(requestId);

		return bookingRequestData;
	}

	@Test
	public void verifyBookingRequest() throws Exception {
		// Test Case 1: Register and Login a new Worker
		String workerToken = this.registerWorkerAndGetToken();
		JSONObject workerLoginData = this.performTokenLogin(workerToken);

		String workerId = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");;

		JSONArray workerSkills = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getJSONObject("workerProfile")
				.getJSONArray("workerSkills");

		// Test Case 2: Register and Login a new Customer
		String customerToken = this.registerCustomerAndGetToken();
		JSONObject customerLoginData = this.performTokenLogin(customerToken);

		String customerId = customerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");

		String workDetailsId = workerSkills.getString(0);

		WorkRequests workRequest = WorkRequests.create();
		workRequest.setBiddingTier(0);
		workRequest.setWorkDetailsId(workDetailsId);
		workRequest.setCustomerId(customerId);
		workRequest.setWorkerId(workerId);

		// Test Case 3: Create booking request
		MvcResult bookingRequestResult = this.mvc.perform(
				MockMvcRequestBuilders.post("/work/create")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workRequest))
						.header("Authorization", "Bearer " + customerToken)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data.customerId").value(workRequest.getCustomerId()),
				MockMvcResultMatchers.jsonPath("$.data.workerId").value(workRequest.getWorkerId()),
				MockMvcResultMatchers.jsonPath("$.data.workDetailsId").value(workDetailsId),
				MockMvcResultMatchers.jsonPath("$.data.biddingTier").value(workRequest.getBiddingTier())
		).andReturn();

		JSONObject bookingRequestObject = new JSONObject(bookingRequestResult.getResponse().getContentAsString());
		String requestId = bookingRequestObject.getJSONObject("data").getString("requestId");

		// Test Case 4: Verify creation of Customer Booking with Pending Status
		this.getCustomerBookings(customerToken)
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings").isNotEmpty(),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].bookingId").value(requestId),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].status").value(0)
				);

		// Test Case 5: Verify creation of Worker Request
		this.getWorkerRequests(workerToken)
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings").isNotEmpty(),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].requestId").value(requestId),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].workDetailsId").value(workDetailsId)
				);
	}

	private ResultActions createApprovedBooking(BookingRequestData bookingRequestData) throws Exception {
		WorkApproval workApproval = new WorkApproval();
		workApproval.setRequestId(bookingRequestData.getRequestId());
		workApproval.setIsApproved(true);
		this.mvc.perform(
				MockMvcRequestBuilders.post("/work/approve")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workApproval))
						.header("Authorization", "Bearer " + bookingRequestData.getWorkerToken())
		);

		return this.getCustomerBookings(bookingRequestData.getCustomerToken())
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.data.listings").isNotEmpty()
				);
	}

	// Scenario 1
	@Test
	public void registerAndVerifyCustomer() throws Exception {
		String token = this.registerCustomerAndGetToken();

		// Test Case 1: Token Login Customer
		this.performTokenLogin(token);
	}

	// Scenario 2
	@Test
	public void registerAndVerifyWorker() throws Exception {
		String token = this.registerWorkerAndGetToken();

		// Test Case 1: Token Login Worker
		this.performTokenLogin(token);
	}

	// Scenario 3
	@Test
	public void newCustomerHasNoBookings() throws Exception {
		// Test Case 1: Register and Login a new Customer
		String customerToken = this.registerCustomerAndGetToken();

		// Test Case 2: Verify new Customer's bookings
		this.getCustomerBookings(customerToken)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.data.listings").isEmpty()
				);
	}

	// Scenario 4
	@Test
	public void newWorkerHasNoBookings() throws Exception {
		// Test Case 1: Register and Login a new Customer
		String workerToken = this.registerWorkerAndGetToken();

		// Test Case 2: Verify new Customer's bookings
		this.getWorkerBookings(workerToken)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.data").isEmpty()
				);
	}

	// Scenario 5
	@Test
	public void newWorkerHasNoRequests() throws Exception {
		// Test Case 1: Register and Login a new Customer
		String workerToken = this.registerWorkerAndGetToken();

		// Test Case 2: Verify new Customer's bookings
		this.getWorkerRequests(workerToken).andExpect(
				MockMvcResultMatchers.jsonPath("$.data.listings").isEmpty()
		);
	}

	// Scenario 6
	@Test
	public void createWorkerAndFindWork() throws Exception {
		// Test Case 1: Register and Login a new Worker
		String workerToken = this.registerWorkerAndGetToken();
		JSONObject workerLoginData = this.performTokenLogin(workerToken);

		String workerId = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");

		JSONArray workerSkills = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getJSONObject("workerProfile")
				.getJSONArray("workerSkills");

		// Test Case 2: Register and Login a new Customer
		String customerToken = this.registerCustomerAndGetToken();
		JSONObject customerLoginData = this.performTokenLogin(customerToken);

		String customerId = customerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");

		String workDetailsId = workerSkills.getString(0);

		WorkerFindRequest workerFindRequest = new WorkerFindRequest();
		workerFindRequest.setCustomerId(customerId);
		workerFindRequest.setWorkDetailsId(workDetailsId);

		this.mvc.perform(
				MockMvcRequestBuilders.post("/worker/find")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workerFindRequest))
						.header("Authorization", "Bearer " + customerToken)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data").isNotEmpty(),
				MockMvcResultMatchers.jsonPath("$.data[?(@.workerProfile.userId == '" + workerId + "')]").exists()
		);
	}

	// Scenario 7
	@Test
	public void createWorkerAndDontFindWork() throws Exception {
		// Test Case 1: Register and Login a new Worker
		String workerToken = this.registerWorkerAndGetToken();
		JSONObject workerLoginData = this.performTokenLogin(workerToken);

		String workerId = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");

		JSONArray workerDomainArray = workerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getJSONObject("workerProfile")
				.getJSONArray("workerDomains");

		HashSet<Integer> workDomains = new HashSet<>();
		int excludedDomain = 0;
		for (int i = 0; i < workerDomainArray.length(); i++) {
			workDomains.add(workerDomainArray.getInt(i));
		}
		for (int i = 0; i < 5; i++) {
			if (!workDomains.contains(i)) {
				excludedDomain = i;
				break;
			}
		}

		List<WorkDetails> workDetailsList = this.workDetailsRepository.findAllByDomain(excludedDomain);

		String customerToken = this.registerCustomerAndGetToken();
		JSONObject customerLoginData = this.performTokenLogin(customerToken);

		String customerId = customerLoginData
				.getJSONObject("data")
				.getJSONObject("profile")
				.getString("userId");

		String workDetailsId = workDetailsList.getFirst().getId();

		WorkerFindRequest workerFindRequest = new WorkerFindRequest();
		workerFindRequest.setCustomerId(customerId);
		workerFindRequest.setWorkDetailsId(workDetailsId);

		// Test Case 2: Register and Login a new Customer
		this.mvc.perform(
				MockMvcRequestBuilders.post("/worker/find")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workerFindRequest))
						.header("Authorization", "Bearer " + customerToken)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data[?(@.workerProfile.userId == '" + workerId + "')]").doesNotExist()
		);
	}

	// Scenario 8
	@Test
	public void createAndRejectWorkRequest() throws Exception {
		// Test Case 1-5: Create Booking Request
		BookingRequestData bookingRequestData = this.createBookingRequest();

		WorkApproval workApproval = new WorkApproval();
		workApproval.setRequestId(bookingRequestData.getRequestId());
		workApproval.setIsApproved(false);

		// Test Case 6: Reject Booking Request
		this.mvc.perform(
				MockMvcRequestBuilders.post("/work/approve")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workApproval))
						.header("Authorization", "Bearer " + bookingRequestData.getWorkerToken())
		);

		// Test Case 7: Verify new Customer's bookings
		this.getCustomerBookings(bookingRequestData.getCustomerToken())
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.data.listings").isEmpty()
				);
	}

	// Scenario 9
	@Test
	public void createAndAcceptWorkRequest() throws Exception {
		// Test Case 1-5: Create Booking Request
		BookingRequestData bookingRequestData = this.createBookingRequest();

		// Test Case 6: Approve Booking
		this.createApprovedBooking(bookingRequestData);

		// Test Case 7: Verify new Customer's bookings
		this.getCustomerBookings(bookingRequestData.getCustomerToken())
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings").isNotEmpty(),
						MockMvcResultMatchers.jsonPath("$.data.listings[0].status").value(1)
				);
	}

	// Scenario 10
	@Test
	public void createAndCompleteWorkBooking() throws Exception {
		// Test Case 1-5: Create Booking Request
		BookingRequestData bookingRequestData = this.createBookingRequest();

		// Test Case 6: Approve Booking
		ResultActions bookingsResult = this.createApprovedBooking(bookingRequestData);

		JSONObject bookingsObject = new JSONObject(bookingsResult.andReturn().getResponse().getContentAsString());
		String bookingId = bookingsObject
				.getJSONObject("data")
				.getJSONArray("listings")
				.getJSONObject(0)
				.getString("bookingId");

		WorkCompletion workCompletion = new WorkCompletion();
		workCompletion.setBookingId(bookingId);
		workCompletion.setIsReported(false);

		// Test Case 7: Complete Booking
		this.mvc.perform(
				MockMvcRequestBuilders.post("/work/complete")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workCompletion))
						.header("Authorization", "Bearer " + bookingRequestData.getCustomerToken())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);
	}

	// Scenario 11
	@Test
	public void createAndBanReportedWorkBooking() throws Exception {
		// Test Case 1-5: Create Booking Request
		BookingRequestData bookingRequestData = this.createBookingRequest();

		// Test Case 6: Approve Booking
		ResultActions bookingsResult = this.createApprovedBooking(bookingRequestData);

		JSONObject bookingsObject = new JSONObject(bookingsResult.andReturn().getResponse().getContentAsString());
		String bookingId = bookingsObject
				.getJSONObject("data")
				.getJSONArray("listings")
				.getJSONObject(0)
				.getString("bookingId");

		WorkCompletion workCompletion = new WorkCompletion();
		workCompletion.setBookingId(bookingId);
		workCompletion.setIsReported(true);
		workCompletion.setReportReason(UUID.randomUUID().toString());

		// Test Case 7: Report Booking
		this.mvc.perform(
				MockMvcRequestBuilders.post("/work/complete")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workCompletion))
						.header("Authorization", "Bearer " + bookingRequestData.getCustomerToken())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);

		this.getCustomerBookings(bookingRequestData.getCustomerToken())
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings[?(@.bookingId == '" + bookingId + "')]").exists(),
						MockMvcResultMatchers.jsonPath("$.data.listings[?(@.bookingId == '" + bookingId + "')].status").value(2)
				);

		String administratorToken = this.getTestingAdministratorToken();
		MvcResult reportResult = this.mvc.perform(
				MockMvcRequestBuilders.get("/admin/reports/0")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data[?(@.booking.bookingId == '" + bookingId + "')]").exists()
		).andReturn();

		JSONObject reportObject = new JSONObject(reportResult.getResponse().getContentAsString());
		String reportId = reportObject.getJSONArray("data").getJSONObject(0).getString("reportId");

		AdministratorAction administratorAction = new AdministratorAction();
		administratorAction.setReportId(reportId);
		administratorAction.setAdministratorId("63e5460c-38a0-4c1c-abac-595b36945d7a");
		administratorAction.setActionType(0);

		this.mvc.perform(
				MockMvcRequestBuilders.post("/admin/act")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(administratorAction))
						.header("Authorization", "Bearer " + administratorToken)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);

		this.mvc.perform(
				MockMvcRequestBuilders.get("/admin/reports/0")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data[?(@.reportId == '" + reportId + "')]").doesNotExist(),
				MockMvcResultMatchers.jsonPath("$.data[?(@.booking.bookingId == '" + bookingId + "')]").doesNotExist()
		);

		TokenLogin tokenLogin = new TokenLogin();
		tokenLogin.setToken(bookingRequestData.getWorkerToken());

		this.mvc.perform(
				MockMvcRequestBuilders.post("/authenticate/token")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(tokenLogin))
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(true)
		);
	}

	// Scenario 11
	@Test
	public void createAndSuspendReportedWorkBooking() throws Exception {
		// Test Case 1-5: Create Booking Request
		BookingRequestData bookingRequestData = this.createBookingRequest();

		// Test Case 6: Approve Booking
		ResultActions bookingsResult = this.createApprovedBooking(bookingRequestData);

		JSONObject bookingsObject = new JSONObject(bookingsResult.andReturn().getResponse().getContentAsString());
		String bookingId = bookingsObject
				.getJSONObject("data")
				.getJSONArray("listings")
				.getJSONObject(0)
				.getString("bookingId");

		WorkCompletion workCompletion = new WorkCompletion();
		workCompletion.setBookingId(bookingId);
		workCompletion.setIsReported(true);
		workCompletion.setReportReason(UUID.randomUUID().toString());

		// Test Case 7: Report Booking
		this.mvc.perform(
				MockMvcRequestBuilders.post("/work/complete")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workCompletion))
						.header("Authorization", "Bearer " + bookingRequestData.getCustomerToken())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);

		this.getCustomerBookings(bookingRequestData.getCustomerToken())
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings[?(@.bookingId == '" + bookingId + "')]").exists(),
						MockMvcResultMatchers.jsonPath("$.data.listings[?(@.bookingId == '" + bookingId + "')].status").value(2)
				);

		String administratorToken = this.getTestingAdministratorToken();
		MvcResult reportResult = this.mvc.perform(
				MockMvcRequestBuilders.get("/admin/reports/0")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data[?(@.booking.bookingId == '" + bookingId + "')]").exists()
		).andReturn();

		JSONObject reportObject = new JSONObject(reportResult.getResponse().getContentAsString());
		String reportId = reportObject.getJSONArray("data").getJSONObject(0).getString("reportId");

		AdministratorAction administratorAction = new AdministratorAction();
		administratorAction.setReportId(reportId);
		administratorAction.setAdministratorId("63e5460c-38a0-4c1c-abac-595b36945d7a");
		administratorAction.setActionType(1);

		this.mvc.perform(
				MockMvcRequestBuilders.post("/admin/act")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(administratorAction))
						.header("Authorization", "Bearer " + administratorToken)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);

		this.mvc.perform(
				MockMvcRequestBuilders.get("/admin/reports/0")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data[?(@.reportId == '" + reportId + "')]").doesNotExist(),
				MockMvcResultMatchers.jsonPath("$.data[?(@.booking.bookingId == '" + bookingId + "')]").doesNotExist()
		);

		TokenLogin tokenLogin = new TokenLogin();
		tokenLogin.setToken(bookingRequestData.getWorkerToken());

		this.mvc.perform(
				MockMvcRequestBuilders.post("/authenticate/token")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(tokenLogin))
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(true)
		);
	}

	// Scenario 11
	@Test
	public void createAndIgnoreReportedWorkBooking() throws Exception {
		// Test Case 1-5: Create Booking Request
		BookingRequestData bookingRequestData = this.createBookingRequest();

		// Test Case 6: Approve Booking
		ResultActions bookingsResult = this.createApprovedBooking(bookingRequestData);

		JSONObject bookingsObject = new JSONObject(bookingsResult.andReturn().getResponse().getContentAsString());
		String bookingId = bookingsObject
				.getJSONObject("data")
				.getJSONArray("listings")
				.getJSONObject(0)
				.getString("bookingId");

		WorkCompletion workCompletion = new WorkCompletion();
		workCompletion.setBookingId(bookingId);
		workCompletion.setIsReported(true);
		workCompletion.setReportReason(UUID.randomUUID().toString());

		// Test Case 7: Report Booking
		this.mvc.perform(
				MockMvcRequestBuilders.post("/work/complete")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(workCompletion))
						.header("Authorization", "Bearer " + bookingRequestData.getCustomerToken())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);

		this.getCustomerBookings(bookingRequestData.getCustomerToken())
				.andExpectAll(
						MockMvcResultMatchers.jsonPath("$.data.listings[?(@.bookingId == '" + bookingId + "')]").exists(),
						MockMvcResultMatchers.jsonPath("$.data.listings[?(@.bookingId == '" + bookingId + "')].status").value(2)
				);

		String administratorToken = this.getTestingAdministratorToken();
		MvcResult reportResult = this.mvc.perform(
				MockMvcRequestBuilders.get("/admin/reports/0")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data[?(@.booking.bookingId == '" + bookingId + "')]").exists()
		).andReturn();

		JSONObject reportObject = new JSONObject(reportResult.getResponse().getContentAsString());
		String reportId = reportObject.getJSONArray("data").getJSONObject(0).getString("reportId");

		AdministratorAction administratorAction = new AdministratorAction();
		administratorAction.setReportId(reportId);
		administratorAction.setAdministratorId("63e5460c-38a0-4c1c-abac-595b36945d7a");
		administratorAction.setActionType(2);

		this.mvc.perform(
				MockMvcRequestBuilders.post("/admin/act")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(administratorAction))
						.header("Authorization", "Bearer " + administratorToken)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);

		this.mvc.perform(
				MockMvcRequestBuilders.get("/admin/reports/0")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false),
				MockMvcResultMatchers.jsonPath("$.data[?(@.reportId == '" + reportId + "')]").doesNotExist(),
				MockMvcResultMatchers.jsonPath("$.data[?(@.booking.bookingId == '" + bookingId + "')]").doesNotExist()
		);

		TokenLogin tokenLogin = new TokenLogin();
		tokenLogin.setToken(bookingRequestData.getWorkerToken());

		this.mvc.perform(
				MockMvcRequestBuilders.post("/authenticate/token")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(new ObjectMapper().writeValueAsString(tokenLogin))
		).andExpectAll(
				MockMvcResultMatchers.jsonPath("$.errored").value(false)
		);
	}
}
