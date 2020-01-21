package co.hmtt.binschedule.services.nextbins.cartridges

import co.hmtt.binschedule.model.BinData
import co.hmtt.binschedule.utils.Browser
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat


@Component
class Stirlingshire implements ServicingCouncil {

    final Browser browser

    Stirlingshire(final Browser browser) {
        this.browser = browser
    }

    @Override
    List<BinData> getAllBinDates(def customer) {

        def bds = new ArrayList<BinData>()
        def driver = browser.getBrowser()
        try {
            driver.get customer.lookupUrl
            driver.findElementByClassName("bins").getText().split('\n').each {
                if (it.contains('Grey') || it.contains('Blue') || it.contains('Brown') || it.contains('Green') || it.contains('Glass')) {
                    String[] results = it.split("([b|B]in)|Box")
                    bds.add(new BinData(colour: results[0].trim(), collectionDate: convertToDate(results[1].trim())))
                }
            }
        } finally {
            driver.quit()
        }
        bds
    }

    static Date convertToDate(String date) {
        new SimpleDateFormat("EEEE dd MMMM yyyy").parse(date)
    }
}
