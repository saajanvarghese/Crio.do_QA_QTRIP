package qtriptest.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import qtriptest.SeleniumWrapper;

public class AdventureDetailsPage {

    WebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement guest_Name;

    @FindBy(xpath = "//input[@name='date']")
    private WebElement reserveDate;

    @FindBy(xpath = "//input[@name='person']")
    private WebElement personCount;

    @FindBy(xpath = "//button[@class='reserve-button']")
    private WebElement reservebtn;

    @FindBy(id = "reserved-banner")
    private WebElement confirmReservation;

    @FindBy(xpath = "//div[@class='alert alert-success']//a")
    private WebElement linkElement;

    public AdventureDetailsPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
    }

    public Boolean bookAdventure(String guestName, String date, String count) throws InterruptedException{

        SeleniumWrapper.clickAction(guest_Name, driver);  // Click Name TextBox
        SeleniumWrapper.enterText(guest_Name, guestName); // Enter Guest Name

        Thread.sleep(1000);

        SeleniumWrapper.clickAction(reserveDate, driver);  //Click Date Button

        Thread.sleep(1000);

        SeleniumWrapper.enterText(reserveDate, date); //Enter Date

        Thread.sleep(1000);

        SeleniumWrapper.clickAction(personCount, driver); //Click Person Count
        SeleniumWrapper.enterText(personCount, count);  // Enter Person Count

        Thread.sleep(1000);

        SeleniumWrapper.clickAction(reservebtn, driver);  //Click Reserve Button

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reserved-banner")));

        String confirmReservationtxt = confirmReservation.getText();

        Assert.assertTrue(confirmReservationtxt.contains("successful") , "Adventure Booking Successful");

        return true;
    }
    public void navigateToHistoryPage() throws InterruptedException{
        linkElement.click();

    }
}
