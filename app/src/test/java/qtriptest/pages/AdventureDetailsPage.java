package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class AdventureDetailsPage {

    WebDriver driver;
    public AdventureDetailsPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
    }

    public void bookAdventure(String guestName, String date, String count) throws InterruptedException{

        WebElement guest_Name = driver.findElement(By.xpath("//input[@name='name']"));
        guest_Name.click();

        guest_Name.sendKeys(guestName);

        Thread.sleep(1000);

        WebElement reserveDate = driver.findElement(By.xpath("//input[@name='date']"));

        reserveDate.click();

        Thread.sleep(1000);

        reserveDate.sendKeys(date);

        Thread.sleep(1000);

        WebElement personCount = driver.findElement(By.xpath("//input[@name='person']"));

        personCount.click();

        personCount.sendKeys(count);

        Thread.sleep(1000);

        WebElement reservebtn = driver.findElement(By.xpath("//button[@class='reserve-button']"));

        reservebtn.click();

        Thread.sleep(3000);

        WebElement confirmReservation = driver.findElement(By.id("reserved-banner"));

        String confirmReservationtxt = confirmReservation.getText();

        Assert.assertTrue(confirmReservationtxt.contains("successful") , "Adventure Booking Successful");

    }

}
