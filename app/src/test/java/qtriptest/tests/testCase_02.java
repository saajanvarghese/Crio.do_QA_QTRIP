package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_02 {

    public static String lastGeneratedUserName;
    static WebDriver driver;

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

   // Initialize webdriver for our unit tests
    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException, InterruptedException {
        logStatus("driver", "Initializing driver", "Started");

        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        System.out.println("Hashcode of driver is" + driver.hashCode());

        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().window().maximize();
        Thread.sleep(3000);

        logStatus("driver", "Initializing driver", "Success");     
    }
    
   @Test(description = "Verify the functionality of search filters", dataProvider = "data-provider", dataProviderClass = DP.class,  priority = 2, groups={"Search and Filter flow"}, enabled = true)
    public static void TestCase02(String city, String category_filter, String duration_filter, String expectedFilterResults, String expectedUnfilterResults) throws InterruptedException {

        Boolean status;
        try {
            logStatus("Page Test", "TestCase02 Validation", "Started");
            HomePage home = new HomePage(driver);

            home.navigateToHomePage();

           status = home.searchCity(city);

           if(status){
            logStatus("Page Test", "Search city Functionality Successful", "Success");
           }
           else{
            logStatus("Page Test", "Search city Functionality Failed", "Fail");
           }

           Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?" +"city=" + city.toLowerCase()), "Success");

          status = home.check_filtered_results(category_filter, duration_filter, expectedFilterResults);
          if(status){
            logStatus("Page Test", "Check Filtered Results Successful", "Success");
           }
           else{
            logStatus("Page Test", "Check Filtered Results Failed", "Fail");
           }
           Assert.assertTrue(status, "Check Filtered Results Failed");

           status = home.check_unfiltered_results(expectedUnfilterResults);
           if(status){
            logStatus("Page Test", "Check UnFiltered Results Successful", "Success");
           }
           else{
            logStatus("Page Test", "Check UnFiltered Results Failed", "Fail");
           }
           Assert.assertTrue(status, "Check Filtered Results Failed");

        } catch (Exception e) {
            //TODO: handle exception
            logStatus("Page Test", "Test 2 Validation", "failed");
            e.printStackTrace();
        }

    }

    @AfterSuite(enabled = false)
        public static void quitDriver() throws MalformedURLException {
        driver.close();
        driver.quit();
        logStatus("driver", "Quiting Driver", "Success");
    }

}
