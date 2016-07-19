package com.example.joeymejias.myapplication;

/**
 * Created by joey on 7/18/16.
 */
public class Job {

    String ssn;
    String company;
    int salary;
    int experience;

    public Job(){}

    public Job(String ssn, String company, int salary, int experience) {
        this.ssn = ssn;
        this.company = company;
        this.salary = salary;
        this.experience = experience;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}