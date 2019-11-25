package com.example.homework.domain.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShortContactTest {
    private ShortContact shortContact;
    @Before
    public void setUp() throws Exception {
        shortContact = new ShortContact("1488","Tark", "88005553535");
    }

    @Test
    public void getId() {
        assertEquals("1488",shortContact.getId());
    }

    @Test
    public void setId() {
        shortContact.setId("12");
        assertEquals("12",shortContact.getId());
        shortContact.setId(null);
        assertNull(null,shortContact.getId());

    }

    @Test
    public void getName() {
        assertEquals("Tark",shortContact.getName());

    }

    @Test
    public void setName() {
        shortContact.setName("Pomidor");
        assertEquals("Pomidor",shortContact.getName());
        shortContact.setName(null);
        assertNull(null,shortContact.getName());
    }

    @Test
    public void getPhone() {
        assertEquals("88005553535",shortContact.getPhone());
    }

    @Test
    public void setPhone() {
        shortContact.setPhone("2281488");
        assertEquals("2281488",shortContact.getPhone());
        shortContact.setName(null);
        assertNull(null,shortContact.getName());
    }
}