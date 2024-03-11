package qtriptest.pages;

import java.time.Duration;
import java.util.List;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HistoryPage {
    WebDriver driver;

    @FindBy(xpath = "//tbody[@id='reservation-table']//tr//th")
    private WebElement transaction_id;

    @FindBy(className  = "cancel-button")
    private WebElement cancelbtn;
    
    public HistoryPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
    }

    public void navigateToHistoryPage(){

        String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html";

        if(this.driver.getCurrentUrl() != url){
            this.driver.get(url);
        }
    }

    public Boolean transactiondetails() throws InterruptedException{

        String transaction_id_value = transaction_id.getText();

        System.out.println("Transaction ID : "+transaction_id_value);
        cancelbtn.click();

        Thread.sleep(3000);

        driver.navigate().refresh();

        String message = "";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            WebElement refresh_transaction_id =
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='reservation-table']//tr//th")));
            // WebElement is present, perform actions
            // Perform actions on txt1
        } catch (Exception e) {
            // WebElement is not present within the given timeout, handle accordingly
            message = "Transaction ID is not present";
        }

        String finalmessage = message;

        Assert.assertEquals(finalmessage, "Transaction ID is not present","Success: Transaction ID removed after page refresh ");

        return true;
    }

    public Boolean bookingHistoryCheck() throws InterruptedException{

       List <WebElement> reservationTableCheck = driver.findElements(By.xpath("//th[@scope='row']"));

      int reservationsTableCheck = reservationTableCheck.size();

       Assert.assertTrue(reservationsTableCheck == 3, "Verified Reservation Table");

       return true;

}

}
