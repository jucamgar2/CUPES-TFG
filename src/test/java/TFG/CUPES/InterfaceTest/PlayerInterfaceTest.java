package TFG.CUPES.InterfaceTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PlayerInterfaceTest {

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
    public void registerPlayerTest() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1695, 1087));
        driver.findElement(By.linkText("Registrarse")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("Joselillo");
        driver.findElement(By.id("password")).sendKeys("1111");
        driver.findElement(By.id("name")).sendKeys("Joselillo");
        driver.findElement(By.id("mail")).sendKeys("jose@jose.com");
        driver.findElement(By.className("buttom")).click();
    }

    @Test
    public void loginTest(){
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1695, 1087));
        driver.findElement(By.linkText("Iniciar sesión")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("Guaje");
        driver.findElement(By.id("password")).sendKeys("1111ñ@A?");
        driver.findElement(By.className("buttom")).click();
        driver.findElement(By.linkText("Cerrar sesión")).click();
    }

    @Test
    public void loginFailTest(){
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1695, 1087));
        driver.findElement(By.linkText("Iniciar sesión")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("Guaje");
        driver.findElement(By.id("password")).sendKeys("hola");
        driver.findElement(By.className("buttom")).click();
        driver.findElement(By.className("error-notification"));
    }

    @Test
    public void editPerfilTest(){
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1695, 1087));
        driver.findElement(By.linkText("Registrarse")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("Joselillo2");
        driver.findElement(By.id("password")).sendKeys("1111");
        driver.findElement(By.id("name")).sendKeys("Joselillo");
        driver.findElement(By.id("mail")).sendKeys("jose@jose.com");
        driver.findElement(By.className("buttom")).click();
        driver.findElement(By.linkText("Iniciar sesión")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("Joselillo2");
        driver.findElement(By.id("password")).sendKeys("1111");
        driver.findElement(By.className("buttom")).click();
        driver.findElement(By.linkText("Perfil")).click();
        driver.findElement(By.linkText("Editar mi perfil")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Pepe2");
        driver.findElement(By.className("buttom")).click();
        driver.findElement(By.linkText("Perfil")).click();
        driver.findElement(By.xpath("//p[@class='game' and contains(text(), 'Nombre: Pepe2')]"));
    }
}
