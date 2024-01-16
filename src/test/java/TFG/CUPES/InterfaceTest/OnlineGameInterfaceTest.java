package TFG.CUPES.InterfaceTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class OnlineGameInterfaceTest {
    
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriver driver2;

    @Test
    public void onlineGameIntegrationTest() throws InterruptedException{
        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        FirefoxProfile privateProfile = new FirefoxProfile();
        privateProfile.setPreference("browser.privatebrowsing.autostart", true);
        FirefoxOptions privateOptions = new FirefoxOptions();
        privateOptions.setProfile(privateProfile);
        driver2 = new FirefoxDriver(privateOptions);
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1695, 1087));
        driver2.get("http://localhost:8080/");
        driver2.manage().window().setSize(new Dimension(1695, 1087));
        driver.findElement(By.linkText("Iniciar sesión")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("Guaje");
        driver.findElement(By.id("password")).sendKeys("1111ñ@A?");
        driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
        driver.findElement(By.className("buttom")).click();
        driver2.findElement(By.linkText("Iniciar sesión")).click();
        driver2.findElement(By.id("username")).click();
        driver2.findElement(By.id("username")).sendKeys("Antonio");
        driver2.findElement(By.id("password")).sendKeys("1111ñ@A?");
        driver2.findElement(By.id("password")).sendKeys(Keys.ENTER);
        driver2.findElement(By.className("buttom")).click();
        driver.findElement(By.linkText("Jugar")).click();
        driver.get("http://localhost:8080/game/onlineGame/new");
        Thread.sleep(10000);
        //driver2.findElement(By.linkText("Unirse a una partida 1vs1 en línea")).click();
        driver2.get("http://localhost:8080/game/onlineGame/join");
        Thread.sleep(10000);
        driver.findElement(By.linkText("Estoy Listo")).click();
        driver2.findElement(By.linkText("Estoy Listo")).click();
        Thread.sleep(10000);
        for (int i = 0; i < 15; i++) {
            Thread.sleep(1000);
            driver.findElement(By.id("autocompleteInput")).click();
            driver.findElement(By.id("autocompleteInput")).sendKeys("i");
            driver.findElement(By.id("autocompleteInput")).sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            driver2.findElement(By.id("autocompleteInput")).click();
            driver2.findElement(By.id("autocompleteInput")).sendKeys("i");
            driver2.findElement(By.id("autocompleteInput")).sendKeys(Keys.ENTER);
        }
        Thread.sleep(1000);
        
        String url1 = driver2.getCurrentUrl();
        String url = driver.getCurrentUrl();
        //assert(url1.equals(url));
        //assert(url.contains("http://localhost:8080/game/onlineGame/finish"));

        driver.quit();
        driver2.quit();
    }

}
