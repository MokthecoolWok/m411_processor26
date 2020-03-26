package ch.sbb.cca.domain;

import java.util.Date;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Gender gender;
    private double height;
    private double weight;
    private Date birthday;
    private double BMI;

    public Person(int id, String firstName, String lastName, String eMail, Gender gender, double height, double weight, Date birthday, double BMI) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.BMI = BMI;
    }

    public Person(int id, String firstName, String lastName, String eMail, Gender gender, double height, double weight, Date birthDate) {
        this(id, firstName, lastName, eMail, gender, height, weight, birthDate, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }
}
