package qtriptest.api_test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qtriptest.DP;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class testCase_API_03 {

    private String userToken;
    private String userId;

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://qtripdynamic-qa-backend.vercel.azurewebsites.net/";
        RestAssured.basePath = "/api/v1/cities";
    }

    @Test(description = "Verify that a reservation can be made successfully", dataProvider = "data-provider", dataProviderClass = DP.class)
    public void testCase_03() {
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "Password123";

        // Step 1: Create a new user and login
        registerUser(email, password);
        loginUser(email, password);

        // Step 2: Perform a booking using a POST call
        String adventureId = "12345"; // Sample Adventure ID; replace with actual data
        String date = "2025-01-30";
        int numberOfPersons = 2;

        JSONObject bookingRequest = new JSONObject();
        bookingRequest.put("userId", userId);
        bookingRequest.put("adventureId", adventureId);
        bookingRequest.put("date", date);
        bookingRequest.put("persons", numberOfPersons);

        RequestSpecification bookingRequestSpec = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + userToken)
                .body(bookingRequest.toString());

        Response bookingResponse = bookingRequestSpec.post("/api/v1/reservations");
        System.out.println("Booking Request Completed");

        // Step 3: Assert that booking was successful
        Assert.assertEquals(bookingResponse.getStatusCode(), 200, "Booking API failed!");

        // Perform a GET Reservations call to validate booking
        validateBookingListedInReservations();
    }

    private void registerUser(String email, String password) {
        JSONObject registrationRequest = new JSONObject();
        registrationRequest.put("email", email);
        registrationRequest.put("password", password);
        registrationRequest.put("confirmpassword", password);

        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(registrationRequest.toString())
                .log().all();

        Response response = httpRequest.post("/api/v1/register");
        Assert.assertEquals(response.getStatusCode(), 201, "Registration API failed!");
        System.out.println("User registered successfully.");
    }

    private void loginUser(String email, String password) {
        JSONObject loginRequest = new JSONObject();
        loginRequest.put("email", email);
        loginRequest.put("password", password);

        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(loginRequest.toString());

        Response response = httpRequest.post("/api/v1/login");
        Assert.assertEquals(response.getStatusCode(), 200, "Login API failed!");

        userToken = response.jsonPath().getString("token");
        userId = response.jsonPath().getString("userId");
        Assert.assertNotNull(userToken, "User token is missing!");
        Assert.assertNotNull(userId, "User ID is missing!");
        System.out.println("User logged in successfully.");
    }

    private void validateBookingListedInReservations() {
        RequestSpecification httpRequest = RestAssured.given()
                .header("Authorization", "Bearer " + userToken);

        Response response = httpRequest.get("/api/v1/reservations");
        Assert.assertEquals(response.getStatusCode(), 200, "GET Reservations API failed!");

        boolean bookingFound = response.getBody().asString().contains(userId);
        Assert.assertTrue(bookingFound, "Booking was not found in the reservations!");
        System.out.println("Booking found in reservations.");
    }

    @AfterClass
    public void end() {
        System.out.println("Test execution completed");
    }
}
