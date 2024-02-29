package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class AdventurePage {

    WebDriver driver;
    public AdventurePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory, this);
    }

    public void searchCity(String cityName) throws InterruptedException{

        WebElement searchCity = driver.findElement(By.xpath("//input[@class='hero-input']"));
        searchCity.click();
        Thread.sleep(1000);
        searchCity.sendKeys(cityName);
        Thread.sleep(2000);
        WebElement searchSuggestion = driver.findElement(By.xpath("//ul[@id='results']/a"));
        Assert.assertTrue(searchSuggestion.isDisplayed(), "Searched City is Present");

        searchSuggestion.click();

        Thread.sleep(1000);
    }

    public void selectAdventure(String adVentureName) throws InterruptedException{

        WebElement adventuretxtbox = driver.findElement(By.xpath("//input[@id='search-adventures']"));

        adventuretxtbox.click();

        adventuretxtbox.sendKeys(adVentureName);

        Thread.sleep(3000);

        WebElement adventureSearchResults = driver.findElement(By.xpath("//div[@class='activity-card']//img"));

        adventureSearchResults.click();

        Thread.sleep(2000);

    }
    
}
