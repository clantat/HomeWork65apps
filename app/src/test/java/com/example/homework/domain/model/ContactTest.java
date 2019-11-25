package com.example.homework.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ContactTest {

    private Contact contact;

    @Before
    public void setUp() throws Exception {
        contact = new Contact("1488", "Stol", "322", "bidlo", null);

    }

    @Test
    public void getmBitmap() {
        assertNull(null, contact.getmBitmap());
    }

    @Test
    public void getName() {
        assertEquals("Stol", contact.getName());
    }

    @Test
    public void getId() {
        assertEquals("1488", contact.getId());
    }

    @Test
    public void getPhone() {
        assertEquals("322", contact.getPhone());
    }

    @Test
    public void getEmail() {
        assertEquals("bidlo", contact.getEmail());
    }

    @Test
    public void setName() {
        contact.setName("bong");
        assertEquals("bong", contact.getName());
        contact.setName(null);
        assertNull(null, contact.getName());
    }

    @Test
    public void setPhone() {
        contact.setPhone("12");
        assertEquals("12", contact.getPhone());
        contact.setPhone(null);
        assertNull(null, contact.getPhone());
    }

    @Test
    public void setEmail() {
        contact.setEmail("nebidlo");
        assertEquals("nebidlo", contact.getEmail());
        contact.setEmail(null);
        assertNull(null, contact.getEmail());
    }

    @Test
    public void setId() {
        contact.setId("122");
        assertEquals("122", contact.getId());
        contact.setId(null);
        assertNull(null, contact.getId());
    }
}