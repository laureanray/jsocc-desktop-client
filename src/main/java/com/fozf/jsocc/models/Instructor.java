package com.fozf.jsocc.models;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Instructor extends User {
    private long id;
    private boolean instructor;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;

    private List<Course> courses = new ArrayList<>();

    public Instructor(){ }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return
        "id: " + this.id  + "\n" +
        "firstName: " + this.firstName  + "\n" +
        "lastName: " + this.lastName  + "\n" +
        "email: " + this.email  + "\n" +
        "password: " + this.password  + "\n" +
        "username: " + this.username  + "\n";
    }
}
