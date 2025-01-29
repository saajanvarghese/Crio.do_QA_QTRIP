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

public class testCase_API_04 {

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://qtripdynamic-qa-backend.vercel.azurewebsites.net/";
        RestAssured.basePath = "/api/v1/register";
    }

    @Test(description = "Verify that duplicate user registration fails", dataProvider = "data-provider", dataProviderClass = DP.class)
    public void testCase_04() {
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "Password123";

        // Step 1: Register a new user
        registerUser(email, password);

        // Step 2: Try registering the same user again
        Response duplicateRegistrationResponse = registerUserWithoutAssertion(email, password);

        // Step 3: Validate the response for duplicate registration
        Assert.assertEquals(duplicateRegistrationResponse.getStatusCode(), 400, "Expected status code 400 for duplicate registration!");
        String errorMessage = duplicateRegistrationResponse.jsonPath().getString("message");
        Assert.assertTrue(errorMessage.contains("User already exists"), "Error message does not indicate duplicate registration!");
        System.out.println("Duplicate registration check passed with appropriate error message.");
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

    private Response registerUserWithoutAssertion(String email, String password) {
        JSONObject registrationRequest = new JSONObject();
        registrationRequest.put("email", email);
        registrationRequest.put("password", password);
        registrationRequest.put("confirmpassword", password);

        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(registrationRequest.toString())
                .log().all();

        return httpRequest.post("/api/v1/register");
    }

    @AfterClass
    public void end() {
        System.out.println("Test execution completed");
    }
}
