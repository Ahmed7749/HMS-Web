package com.hospital.pojos;

import com.hospital.Enums.Genders;
import com.hospital.utils.Validations;
import java.time.LocalDate;
import java.util.Random;
public class Patient{
    private String name;
    private int id;
    private Genders gender;
    private LocalDate birthDate;
    private String middleName;
    private String lastName;


    public Patient(int id, String name, Genders gender, LocalDate birthDate, String middleName, String lastName) {
        this.id = id;
        this.name = Validations.checkNotNull(name,"Null name has been entered");
        this.gender = Validations.checkNotNull(gender, "Wrong gender has been Entered");
        this.birthDate = Validations.checkNotNull(birthDate, "The user must be born at a date..");
        this.middleName = middleName;
        this.lastName = Validations.checkNotNull(lastName, "User must have a last name");
    }

    public Patient(String name,
                   Genders gender,
                   LocalDate birthDate,
                   String middleName,
                   String lastName) {
        this.name = Validations.checkNotNull(name, "User must have a name");
        this.gender = Validations.checkNotNull(gender, "They are only 2 genders. Pick one");
        this.birthDate = Validations.checkNotNull(birthDate, "User must be born");
        this.middleName = middleName;
        this.lastName = Validations.checkNotNull(lastName, "User must have a family name");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public static Patient getRandomPatient(){
        Random rand = new Random();
        String[] names = {"Ahmed", "Nora", "Assel", "Jhon", "Mansoor"};
        String[] middleNames = {"Maher", "Khalid", "Nasser", "Ali"};
        String[] lastNames = {"Qassem", "Jamous", "Qashalq", "AboMustafa", "Aljafree"};
        return new Patient(
                names[rand.nextInt(names.length)],
                Genders.MALE,
                LocalDate.parse("2006-12-05"),
                middleNames[rand.nextInt(middleNames.length)],
                lastNames[rand.nextInt(lastNames.length)]);
    }


    @Override
    public String toString() {
        System.out.println("-".repeat(110));
        return " %12s %18s | %12s %19d | %12s %12s  \n %12s %12s | %12s %10s | %20s %10s \n%s\n".formatted("Patient's name: ",name,"Patient's id: ",id,"Patient's gender: ",gender.toString().toLowerCase(),"Patient's birth date: ",birthDate,"Patient's middle name: ",middleName,"Patient's last name: ",lastName, "-".repeat(110));
    }


}
