package com.fulfilment.application.monolith.location;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class LocationGatewayTest {

  @Inject
  LocationGateway gateway;

  @Test
  void shouldResolveExistingLocation() {
    Location location = gateway.resolveByIdentifier("ZWOLLE-001");

    assertNotNull(location);
    assertEquals("ZWOLLE-001", location.identification);
    assertEquals(1, location.maxNumberOfWarehouses);
    assertEquals(40, location.maxCapacity);
  }

  @Test
  void shouldResolveAnotherLocation() {
    Location location = gateway.resolveByIdentifier("AMSTERDAM-002");

    assertNotNull(location);
    assertEquals("AMSTERDAM-002", location.identification);
    assertEquals(3, location.maxNumberOfWarehouses);
    assertEquals(75, location.maxCapacity);
  }

  @Test
  void shouldThrowExceptionForUnknownIdentifier() {
    Exception exception = assertThrows(LocationNotFoundException.class,
            () -> gateway.resolveByIdentifier("UNKNOWN-123"));

    assertTrue(exception.getMessage().contains("UNKNOWN-123"));
  }
}
