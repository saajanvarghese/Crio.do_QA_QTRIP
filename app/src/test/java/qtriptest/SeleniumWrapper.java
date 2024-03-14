package qtriptest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {

    public static boolean enterText(WebElement inputBox, String keysToSend){
        try{
            inputBox.clear();
            inputBox.sendKeys(keysToSend);
            Thread.sleep(1000);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean clickAction(WebElement element, WebDriver driver){
        if(element.isDisplayed()){
            try {
                JavascriptExecutor js=(JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true)", element);
                element.click();
                Thread.sleep(2000);
                return true;
            } catch (Exception e) {
                // TODO: handle exception
                return false;
            }
        }
        return false;
    }

    public static boolean navigate(WebDriver driver, String url){
        try {
            if(driver.getCurrentUrl().equals(url)){
                return true;
            }
            else{
                driver.get(url);
                return driver.getCurrentUrl().equals(url);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public static String captureScreenshot(WebDriver driver) throws IOException{
        File srcFile =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String fullPath = "D:\\Automation Crio projects\\QTRIP\\johnsaj97-ME_QTRIP_QA_V2-master\\app\\src\\test\\resources\\screenshots";
        File desFile = new File(fullPath + File.separator + System.currentTimeMillis() + ".png");
        FileUtils.copyFile(srcFile,desFile);
        String errFilePath = desFile.getAbsolutePath();
        return errFilePath;
    }

}
