package ch.sbb.cca.domain;

import java.util.Date;

public class PersonBuilder {
    private int id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Gender gender;
    private double height;
    private double weight;
    private double bmi = 0;
    private Date birthDate;

    public PersonBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PersonBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder seteMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public PersonBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PersonBuilder setHeight(double height) {
        this.height = height;
        return this;
    }

    public PersonBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public PersonBuilder setBMI(double bmi) {
        this.bmi = bmi;
        return this;
    }

    public PersonBuilder setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Person createPerson() {
        return new Person(id, firstName, lastName, eMail, gender, height, weight, birthDate, bmi);
    }
}