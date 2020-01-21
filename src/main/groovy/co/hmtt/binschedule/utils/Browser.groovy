package co.hmtt.binschedule.utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Component

import java.util.concurrent.TimeUnit

@Component
class Browser {

    static def getBrowser() {
        System.setProperty('webdriver.chrome.driver', '/usr/bin/chromedriver')
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        final WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver
    }

}
