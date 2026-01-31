package com.fulfilment.application.monolith.location;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String identifier) {
        super("Location with identifier '" + identifier + "' not found.");
    }
}

