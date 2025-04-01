// package com.lambdatest.tunnel;

// import java.net.URL;
// import java.util.HashMap;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.remote.RemoteWebDriver;

// import hyperexecute.AfterEach;

// import org.openqa.selenium.remote.DesiredCapabilities;


// public class GFGLoginTest {
//   Tunnel t;
 
//   WebDriver driver = null;
//   public static String status = "passed";

//   String username = "roshank";
//   String access_key = "LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm";

//   @BeforeEach
//   public void setUp() throws Exception {
//    ChromeOptions browserOptions = new ChromeOptions();
//   browserOptions.setPlatformName("Windows 10");
//   browserOptions.setBrowserVersion("133");
//   HashMap<String, Object> ltOptions = new HashMap<String, Object>();
//   ltOptions.put("username", "roshank");
//   ltOptions.put("tunnel", true);
//   ltOptions.put("tunnelName", "MavenSingle");
//   ltOptions.put("accessKey", "LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm");
//   ltOptions.put("project", "Untitled");
//   ltOptions.put("selenium_version", "4.0.0");
//   ltOptions.put("w3c", true);
//   browserOptions.setCapability("LT:Options", ltOptions);

//     // create tunnel instance
//     t = new Tunnel();
//     HashMap<String, String> options = new HashMap<>();
//     options.put("user", username);
//     options.put("key", access_key);
//     options.put("tunnelName", "MavenSingle");

//     // start tunnel
//     t.start(options);
//     driver = new RemoteWebDriver(new URL("http://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"),
//         browserOptions);
//     System.out.println("Started session");
//   }

//   @Test
//   public void testTunnel() throws Exception {
//     // Check LocalHost on XAMPP
//     // Thread.sleep(5000);
//     driver.get("http://localhost:8080");

//     // Let's check that the item we added is added in the list.
//     driver.get("https://google.com");
//     // driver.quit();

//   }

//   @AfterEach
//   public void tearDown() throws Exception {
//     try {
//       // Mark test status as failed or passed
//       // driver.executeScript("lambda-status=" + status);
//   } catch (Exception e) {
//       System.out.println("Error in executing lambda status: " + e.getMessage());
//   }
  
//   if (driver != null) {
//       // Quit the driver
//       driver.quit();
//   }
  
//   try {
//       // Stop the tunnel
//       if (t != null) {
//           t.stop();
//       }
//   } catch (Exception e) {
//       System.out.println("Error stopping Tunnel: " + e.getMessage());
//   }
//   }

// }





package com.lambdatest.tunnel;

import java.net.URL;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GFGLoginTest {
    private Tunnel t;
    private WebDriver driver = null;
    private static final String username = "roshank";
    private static final String access_key = "LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm";

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("Starting test setup...");

        // ✅ 1. Configure Browser Options
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("latest");

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", access_key);
        ltOptions.put("tunnel", true);
        ltOptions.put("tunnelName", "MavenSingle");
        ltOptions.put("project", "DebugTunnelTest");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        ltOptions.put("idleTimeout", 180); // ✅ Prevent idle timeout
        browserOptions.setCapability("lambda:options", ltOptions);

        // ✅ 2. Start Tunnel
        t = new Tunnel();
        HashMap<String, String> tunnelOptions = new HashMap<>();
        tunnelOptions.put("user", username);
        tunnelOptions.put("key", access_key);
        tunnelOptions.put("tunnelName", "MavenSingle");

        try {
            System.out.println("Starting LambdaTest Tunnel...");
            t.start(tunnelOptions);
            System.out.println("Tunnel started successfully.");
        } catch (Exception e) {
            System.err.println("Tunnel start failed: " + e.getMessage());
            throw e;
        }

        // ✅ 3. Start Remote WebDriver
        try {
            System.out.println("Connecting to LambdaTest Remote WebDriver...");
            driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"),
                browserOptions
            );
            System.out.println("Remote WebDriver session started successfully.");
        } catch (Exception e) {
            System.err.println("Failed to start RemoteWebDriver: " + e.getMessage());
            throw e;
        }

        // ✅ 4. Prevent Idle Timeout
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("return document.readyState").equals("complete");
            System.out.println("Session activity confirmed.");
        } catch (Exception e) {
            System.err.println("JavaScript executor failed: " + e.getMessage());
        }
    }

    @Test
    public void testTunnel() throws Exception {
        try {
            System.out.println("Navigating to Localhost...");
            driver.get("http://localhost:8080");

            System.out.println("Navigating to Google...");
            driver.get("https://google.com");
        } catch (Exception e) {
            System.err.println("Navigation failed: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Starting teardown...");

        // ✅ 5. Quit Driver Safely
        try {
            if (driver != null) {
                System.out.println("Closing browser session...");
                driver.close(); // Close the active session
                driver.quit();  // Quit the driver completely
                driver = null; // Explicitly set to null
                System.out.println("Driver quit successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error quitting driver: " + e.getMessage());
        }

        // ✅ 6. Stop Tunnel Safely
        try {
            if (t != null) {
                t.stop();
                t = null; // Explicitly set to null
                System.out.println("Tunnel stopped successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error stopping Tunnel: " + e.getMessage());
        }

        System.out.println("Teardown complete.");
    }
}
