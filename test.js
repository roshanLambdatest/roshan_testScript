const { Builder } = require("selenium-webdriver");

(async function runTest() {
  if (!process.env.LT_USERNAME || !process.env.LT_ACCESS_KEY) {
    console.error("Error: LambdaTest credentials are missing!");
    process.exit(1);
  }

  let driver = await new Builder()
    .usingServer(`https://${process.env.LT_USERNAME}:${process.env.LT_ACCESS_KEY}@hub.lambdatest.com/wd/hub`)
    .withCapabilities({
      browserName: "chrome",
      version: "latest",
      platform: "Windows 10",
      username: process.env.LT_USERNAME,  // Use secret
      accessKey: process.env.LT_ACCESS_KEY,  // Use secret
      tunnel: true,
      tunnelName: "github-tunnel",
    })
    .build();

  try {
    await driver.get("http://localhost:3000");
    console.log("Title: ", await driver.getTitle());
  } catch (error) {
    console.error("Test failed!", error);
  } finally {
    await driver.quit();
  }
})();
