package com.adammajor.springbootdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Person {
    private final UUID id;
    private final String firstName;
    private final String lastName;

    //@JsonProperty tells Spring Boot which JSON property is which parameter
    public Person(UUID id, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Person(Builder builder) {
        id = builder.id;
        firstName = builder.firstName;
        lastName = builder.lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static final class Builder {
        private UUID id;
        private String firstName;
        private String lastName;

        public Builder() {

        }

        public Builder(UUID id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
