package com.hospital.Enums;

public enum Majors {
    SURGEON,
    GENERAL;

    public int getSalary(){
        switch(this){
            case SURGEON -> {
                return 15000;
            }
            case GENERAL -> {
                return 12000;
            }
        }
        return 0;
    }
}
