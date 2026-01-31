package com.fulfilment.application.monolith.stores;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;

@ApplicationScoped
public class StoreEventListener {

    @Inject
    LegacyStoreManagerGateway legacyStoreManagerGateway;

    public void onStoreEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) Store store) {
        // ✅ Called only after DB transaction commits successfully
        if (store.isPersistent()) {
            legacyStoreManagerGateway.updateStoreOnLegacySystem(store);
        } else {
            // If deleted, you could notify legacy system differently
            legacyStoreManagerGateway.updateStoreOnLegacySystem(store);
        }
    }
}

