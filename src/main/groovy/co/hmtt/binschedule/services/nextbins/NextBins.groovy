package co.hmtt.binschedule.services.nextbins

import co.hmtt.binschedule.model.BinData
import co.hmtt.binschedule.services.nextbins.cartridges.ServicingCouncil
import co.hmtt.binschedule.utils.JsonLoader
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import static co.hmtt.binschedule.utils.JsonLoader.fetchCouncils

@Slf4j
@Component
class NextBins {

    final List<ServicingCouncil> binDates
    final JsonLoader jsonLoader

    NextBins(final List<ServicingCouncil> binDates, final JsonLoader jsonLoader) {
        this.binDates = binDates
        this.jsonLoader = jsonLoader
    }

    void getNextBins() {
        try {
            def customer = jsonLoader.fetchCustomer()
            def requiredCouncil = deriveCouncilFromPostcode fetchCouncils().councils, customer
            jsonLoader.write(deriveCollectionDates(requiredCouncil, customer), 'schedule.json')
        } catch (Exception e) {
            log.error(e.getMessage())
        }

    }

    private List<BinData> deriveCollectionDates(requiredCouncil, customer) {
        def collectionDates = deriveServicingCouncil(requiredCouncil).getAllBinDates(customer)
        def firstCollectionDate = collectionDates.sort { a, b -> a.collectionDate <=> b.collectionDate }.get(0)
        collectionDates.findAll { it.collectionDate == firstCollectionDate.collectionDate }.each { it.toBeCollected = true }
        collectionDates
    }

    private ServicingCouncil deriveServicingCouncil(def requiredCouncil) {
        binDates.find {
            it.getClass().simpleName == requiredCouncil.name
        }
    }

    static def deriveCouncilFromPostcode(def councils, customer) {
        councils.find { it.postcodes.contains(customer.postcode.outward) }
    }

}
