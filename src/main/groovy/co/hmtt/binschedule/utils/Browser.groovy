package co.hmtt.binschedule.utils

import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import java.util.concurrent.TimeUnit

@Component
class Browser {

    @Value('${wheelie.selenium.hub}')
    private String hubUrl

    def getBrowser() {
        final DesiredCapabilities capabilities = DesiredCapabilities.chrome()
        def options = new ChromeOptions()
        options.addArguments("disable-infobars")
        capabilities.setCapability(ChromeOptions.CAPABILITY, options)
        final RemoteWebDriver driver = new RemoteWebDriver(new URL("http://${hubUrl}:4444/wd/hub"), capabilities)
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver
    }

}
