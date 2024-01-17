package TFG.CUPES.InterfaceTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class GameAloneInterfaceTest {
    
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        //Configuración driver Firefox
        System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        //Configuración driver Chrome
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        //driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
      public void gameAloneIntegrationTest() throws InterruptedException {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1695, 1087));
        driver.findElement(By.linkText("Jugar")).click();
        driver.findElement(By.linkText("Un jugador")).click();
        driver.findElement(By.id("autocompleteInput")).click();
        //String url = driver.getCurrentUrl();
        //String idt = url.split("/")[5];
        //Integer id = Integer.valueOf(idt.split("\\?")[0]);
        //String token = (idt.split("\\?")[1]);
        //Thread.sleep(1000L);
        //GameAlone gameAlone = this.gameAloneService.getGameByTokenAndId(token,id).orElse(null);
        //driver.findElement(By.id("autocompleteInput")).sendKeys(gameAlone.getSelected().getName());
        Thread.sleep(1000L);
        driver.findElement(By.id("autocompleteInput")).sendKeys("x");
        driver.findElement(By.id("autocompleteInput")).sendKeys(Keys.ENTER);
        Thread.sleep(1000L);
        driver.findElement(By.id("autocompleteInput")).click();
        driver.findElement(By.id("autocompleteInput")).sendKeys("x");
        driver.findElement(By.id("autocompleteInput")).sendKeys(Keys.ENTER);
        Thread.sleep(1000L);
        driver.findElement(By.id("autocompleteInput")).click();
        driver.findElement(By.id("autocompleteInput")).sendKeys("x");
        driver.findElement(By.id("autocompleteInput")).sendKeys(Keys.ENTER);
        Thread.sleep(1000L);
        driver.findElement(By.id("autocompleteInput")).click();
        driver.findElement(By.id("autocompleteInput")).sendKeys("x");
        driver.findElement(By.id("autocompleteInput")).sendKeys(Keys.ENTER);
        Thread.sleep(1000L);
      }
}
