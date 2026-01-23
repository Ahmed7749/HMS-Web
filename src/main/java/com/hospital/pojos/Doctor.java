package com.hospital.pojos;
import com.hospital.Enums.Genders;
import com.hospital.Enums.Majors;
import com.hospital.utils.Validations;

import java.util.Random;



public class Doctor {
    private int id;
    private String name;
    private String lastName;
    private Majors major;
    private Genders gender;
    private int userId;

    public Doctor(int id, String name, String lastName, Majors major, Genders gender, int userId) {
        this.id = id;
        this.name = Validations.checkNotNull(name, "Name cannot be null");
        this.lastName = Validations.checkNotNull(lastName,"Last name cannot be null");
        this.major = Validations.checkNotNull(major, "Doctor must be majored");
        this.gender = Validations.checkNotNull(gender, "The doc must have a gender");
        this.userId = userId;
    }


    public Doctor(String name, String lastName, Majors major, Genders gender) {
        this.name = name;
        this.lastName = lastName;
        this.major = major;
        this.gender = gender;
    }

    public Doctor(String name, String lastName, Majors major, Genders gender, int userId) {
        this.name = Validations.checkNotNull(name, "Name cannot be null");
        this.lastName = Validations.checkNotNull(lastName, "last name cannot be null");
        this.major = Validations.checkNotNull(major, "Doctor must be majored");
        this.gender = Validations.checkNotNull(gender, "Gender must be male or female");
        this.userId = userId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Majors getMajor() {
        return major;
    }

    public void setMajor(Majors major) {
        this.major = major;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString(){
        System.out.println("-".repeat(100));
        return "%12s %15s | %12s %16d |\n%12s %10s | %10s %12s | %12s %10s \n%s\n".formatted("Doctor's name: ", name,"Doctor's id: ", id,"Doctor's last name: ",lastName,"Doctor's gender: ",gender, "Doctor's major: ",major, "-".repeat(100));
    }
}
