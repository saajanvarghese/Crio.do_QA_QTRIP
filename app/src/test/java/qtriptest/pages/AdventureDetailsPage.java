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

public class AdventureDetailsPage {

    WebDriver driver;

    @FindBy(xpath = "//input[@class='hero-input']")
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
        guest_Name.click();

        guest_Name.sendKeys(guestName);

        Thread.sleep(1000);

        reserveDate.click();

        Thread.sleep(1000);

        reserveDate.sendKeys(date);

        Thread.sleep(1000);

        personCount.click();

        personCount.sendKeys(count);

        Thread.sleep(1000);

        reservebtn.click();

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
