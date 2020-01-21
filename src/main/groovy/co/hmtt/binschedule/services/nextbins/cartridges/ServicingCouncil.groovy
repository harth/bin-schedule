package co.hmtt.binschedule.services.nextbins.cartridges

import co.hmtt.binschedule.model.BinData

interface ServicingCouncil {
    List<BinData> getAllBinDates(def customer)
}