package hyperexecute;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.URL;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GFGLoginTest {
    private WebDriver driver;

    // Hardcoded LambdaTest Credentials (⚠ Not Recommended)
    private final String username = "roshank";  
    private final String accessKey = "LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm";

    private final String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";

    // Login credentials for GeeksforGeeks
    private final String email = "roshankumar86788@gmail.com";
    private final String password = "Rk@9451176004";

    @BeforeEach
    public void setUp() throws MalformedURLException {
        WebDriverManager.chromedriver().setup(); // Ensures driver is available

        DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("browserName", "Chrome");
capabilities.setCapability("browserVersion", "dev");
HashMap<String, Object> ltOptions = new HashMap<String, Object>();
ltOptions.put("username", "roshank");
ltOptions.put("accessKey", "LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm");
ltOptions.put("platformName", "Windows 10");
ltOptions.put("project", "Untitled");
capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL(gridURL), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Test
    public void testLogin() throws InterruptedException {
        driver.get("https://auth.geeksforgeeks.org/");

        WebElement emailField = driver.findElement(By.id("luser"));
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Sign In']"));
        loginButton.click();

        Thread.sleep(5000);
        assertTrue(driver.getTitle().contains("GeeksforGeeks"));

        System.out.println("Login Successful on LambdaTest!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

//test



