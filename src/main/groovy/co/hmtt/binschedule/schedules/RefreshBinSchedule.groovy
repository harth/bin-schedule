package co.hmtt.binschedule.schedules

import co.hmtt.binschedule.services.nextbins.NextBins
import groovy.util.logging.Slf4j
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Slf4j
@Component
class RefreshBinSchedule {

    final NextBins nextBins

    RefreshBinSchedule(final NextBins nextBins) {
        this.nextBins = nextBins
    }

    @Scheduled(fixedRate = 1000l)
    void refreshSchedule() {
        log.debug 'Running schedule'
        nextBins.getNextBins()
        log.debug 'Scheduled job complete'
    }

}
