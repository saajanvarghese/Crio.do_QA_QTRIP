package qtriptest;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.relevantcodes.extentreports.ExtentReports;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReportSingleton {
    private static ReportSingleton instanceOfSingleTonReport = null;
    WebDriver driver;
    private ExtentReports report;


    private ReportSingleton(){
System.out.println("I am here");
String timestamp = getTimestamp().replace(":", "_");
String fullPath = "D:\\Automation Crio projects\\QTRIP\\johnsaj97-ME_QTRIP_QA_V2-master\\app\\src\\test\\resources\\reports";
report = new ExtentReports(fullPath + "\\" + timestamp + ".html", true);

    }
    private String getTimestamp() {
        String timestamp = String.valueOf(java.time.LocalDateTime.now());
        //String timestamp = getTimestamp().replace(":", "_");
        return timestamp;
    }

    public static ReportSingleton getInstanceOfSingleTonReportClass() throws MalformedURLException{
        if(instanceOfSingleTonReport == null)
            instanceOfSingleTonReport = new ReportSingleton();
            return instanceOfSingleTonReport;
    }

    public ExtentReports getReport(){
        return report;
    }

    
}