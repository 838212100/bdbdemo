package com.patterncat.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;

import static com.sleepycat.persist.model.Relationship.ONE_TO_ONE;

@Entity
public class Employer {

    @PrimaryKey(sequence = "ID")
    private long id;

    @SecondaryKey(relate = ONE_TO_ONE)
    private String name;

    private Address address;

    public Employer(String name) {
        this.name = name;
    }

    private Employer() {
    } // For deserialization

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
