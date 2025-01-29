package qtriptest.api_test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qtriptest.DP;

public class testCase_API_01 {

    @BeforeClass
    public void init(){
        RestAssured.baseURI = "https://qtripdynamic-qa-backend.vercel.azurewebsites.net/";
        RestAssured.basePath = "/api/v1/register";
    }

    @Test(description = "Verify user registration -login", dataProvider = "data-provider", dataProviderClass = DP.class)
    public static void testCase_01(String Username, String Password){
        JSONObject obj = new JSONObject();
        obj.put("email", Username);
        obj.put("password", Password);
        obj.put("confirmpassword", Password);

        RequestSpecification http = RestAssured.given().header("Content-Type", "application/json").body(obj.toString()).log().all();
        Response resp = http.when().post("register");
        System.out.println("Registration Done");

        Assert.assertEquals(resp.getStatusCode(), 201);

        // Login API
        JSONObject loginRequest = new JSONObject();
        loginRequest.put("email", Username);
        loginRequest.put("password", Password);

        http.body(loginRequest.toString());
        Response loginResponse = http.post("/api/v1/login");
        System.out.println("Login Done");

        // Validations
        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login failed!");
        Assert.assertTrue(loginResponse.jsonPath().getBoolean("success"), "Login success flag missing!");
        Assert.assertNotNull(loginResponse.jsonPath().getString("token"), "Token is missing!");
        Assert.assertNotNull(loginResponse.jsonPath().getString("userId"), "User ID is missing!");
    }

    @AfterClass
    public void end() {
        System.out.println("Test execution completed");
    }        
    }   