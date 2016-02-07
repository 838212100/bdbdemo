package com.patterncat.model;

import com.sleepycat.persist.model.Persistent;

/* A persistent class used in other classes. */
@Persistent
public class Address {
    String street;
    String city;
    String state;
    int zipCode;

    public Address() {
    } // For deserialization

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
