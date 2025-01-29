package qtriptest.api_test;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qtriptest.DP;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

public class testCase_API_02 {
    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://qtripdynamic-qa-backend.vercel.azurewebsites.net/";
        RestAssured.basePath = "/api/v1/cities";
    }

    @Test(description = "Verify city search API returns correct results", dataProvider = "data-provider", dataProviderClass = DP.class)
    public void testCase_02() {
        // Create Request Specification
        RequestSpecification httpRequest = RestAssured.given()
                .queryParam("q", "beng")
                .header("Content-Type", "application/json")
                .log().all();

        // Perform API request
        Response response = httpRequest.get("/api/v1/cities/search");

        // Validations
        System.out.println("Search Request Done");
        Assert.assertEquals(response.getStatusCode(), 200, "Search API failed!");

        // Extract and validate response JSON
        JSONArray results = new JSONArray(response.getBody().asString());
        Assert.assertEquals(results.length(), 1, "Expected exactly 1 result!");

        // Verify description contains "100+ Places"
        JSONObject cityData = results.getJSONObject(0);
        Assert.assertTrue(cityData.getString("description").contains("100+ Places"), 
            "Description does not contain expected text!");

        // Schema validation
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("schemas/search_city_schema.json")));
    }

    @AfterClass
    public void end() {
        System.out.println("Test execution completed");
    }
}
