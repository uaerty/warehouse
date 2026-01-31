package com.fulfilment.application.monolith.products;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProductPersistenceTest {

    @Inject
    EntityManager em;

    @Test
    @Transactional
    void shouldPersistAndFindProduct() {
        Product product = new Product("Table");
        product.description = "Wooden dining table";
        product.price = new BigDecimal("499.99");
        product.stock = 5;

        em.persist(product);
        em.flush();
        em.clear();

        Product found = em.find(Product.class, product.id);

        assertNotNull(found);
        assertEquals("Table", found.name);
        assertEquals("Wooden dining table", found.description);
        assertEquals(new BigDecimal("499.99"), found.price);
        assertEquals(5, found.stock);
    }
}

