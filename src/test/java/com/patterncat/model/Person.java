package com.patterncat.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;

import java.util.HashSet;
import java.util.Set;

import static com.sleepycat.persist.model.DeleteAction.NULLIFY;
import static com.sleepycat.persist.model.Relationship.MANY_TO_MANY;
import static com.sleepycat.persist.model.Relationship.MANY_TO_ONE;
import static com.sleepycat.persist.model.Relationship.ONE_TO_MANY;

/* An entity class. */
@Entity
public class Person {

    @PrimaryKey
    String ssn;

    String name;
    Address address;

    @SecondaryKey(relate = MANY_TO_ONE, relatedEntity = Person.class)
    String parentSsn;

    @SecondaryKey(relate = ONE_TO_MANY)
    Set<String> emailAddresses = new HashSet<String>();

    @SecondaryKey(relate = MANY_TO_MANY,
            relatedEntity = Employer.class,
            onRelatedEntityDelete = NULLIFY)
    Set<Long> employerIds = new HashSet<Long>();

    public Person(String name, String ssn, String parentSsn) {
        this.name = name;
        this.ssn = ssn;
        this.parentSsn = parentSsn;
    }

    private Person() {
    } // For deserialization

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public String getParentSsn() {
        return parentSsn;
    }

    public void setParentSsn(String parentSsn) {
        this.parentSsn = parentSsn;
    }

    public Set<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(Set<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public Set<Long> getEmployerIds() {
        return employerIds;
    }

    public void setEmployerIds(Set<Long> employerIds) {
        this.employerIds = employerIds;
    }
}
