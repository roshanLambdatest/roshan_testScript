package com.lambdatest.tunnel;

import java.net.URL;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import hyperexecute.AfterEach;

import org.openqa.selenium.remote.DesiredCapabilities;


public class GFGLoginTest {
  Tunnel t;
 
  WebDriver driver = null;
  public static String status = "passed";

  String username = "roshank";
  String access_key = "LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm";

  @BeforeEach
  public void setUp() throws Exception {
   ChromeOptions browserOptions = new ChromeOptions();
  browserOptions.setPlatformName("Windows 10");
  browserOptions.setBrowserVersion("133");
  HashMap<String, Object> ltOptions = new HashMap<String, Object>();
  ltOptions.put("username", "roshank");
  ltOptions.put("tunnel", true);
  ltOptions.put("tunnelName", "MavenSingle");
  ltOptions.put("accessKey", "LT_0uuZ6lYn8sxQ55aGYiXLDG3lwakfEsccxuWBBfm4EeMb4Wm");
  ltOptions.put("project", "Untitled");
  ltOptions.put("selenium_version", "4.0.0");
  ltOptions.put("w3c", true);
  browserOptions.setCapability("LT:Options", ltOptions);

    // create tunnel instance
    t = new Tunnel();
    HashMap<String, String> options = new HashMap<>();
    options.put("user", username);
    options.put("key", access_key);
    options.put("tunnelName", "MavenSingle");

    // start tunnel
    t.start(options);
    driver = new RemoteWebDriver(new URL("http://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"),
        browserOptions);
    System.out.println("Started session");
  }

  @Test
  public void testTunnel() throws Exception {
    // Check LocalHost on XAMPP
    // Thread.sleep(5000);
    driver.get("http://localhost:8080");

    // Let's check that the item we added is added in the list.
    driver.get("https://google.com");
  }

  @AfterEach
  public void tearDown() throws Exception {
    ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
    driver.quit();
    // close tunnel
    t.stop();
  }
}
