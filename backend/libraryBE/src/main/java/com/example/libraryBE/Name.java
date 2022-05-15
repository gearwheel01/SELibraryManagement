package com.example.libraryBE;

import javax.persistence.Embeddable;

@Embeddable
public class Name {
    private final String firstName;
    private final String lastName;

    public Name() {
        firstName = "asdf";
        lastName = "asdf";
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
