import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class PasteBinTest {

    @Test
    public void createNewPaste() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://pastebin.com/");

        String code = """
                git config --global user.name  "New Sheriff in Town"
                git reset $(git commit-tree HEAD^{tree} -m "Legacy code")
                git push origin master --force""";
        WebElement pasteData = driver.findElement(By.id("postform-text"));
        pasteData.sendKeys(code);

        driver.findElement(By.xpath("(//span[@class='select2-selection__arrow'])[2]")).click();
        driver.findElement(By.xpath("//li[text()='Bash']")).click();
        driver.findElement(By.xpath("(//span[@class='select2-selection__arrow'])[3]")).click();
        driver.findElement(By.xpath("//li[text()='10 Minutes']")).click();
        String pasteName = "how to gain dominance among developers";
        WebElement pasteNameElem = driver.findElement(By.id("postform-name"));
        pasteNameElem.sendKeys(pasteName);

        driver.findElement(By.xpath("//button[@class=\"btn -big\"]")).click();


        String name=driver.findElement(By.tagName("h1")).getText();
        String syntax=driver.findElement(By.xpath("//a[@class=\"btn -small h_800\"]")).getText();
        String pasteText=driver.findElement(By.tagName("ol")).getText();

        //checking paste is created or not
        try {
            WebElement element=driver.findElement(By.xpath("//div[@class='notice -success -post-view']"));
            String text = element.getText();
            System.out.println(text);
        } catch (NoSuchElementException e) {
            Assert.fail();
        }

        //verifying details of paste
        Assert.assertEquals(name,pasteName);
        Assert.assertEquals(syntax,"Bash");
        Assert.assertEquals(pasteText,code);
        driver.quit();
    }


}