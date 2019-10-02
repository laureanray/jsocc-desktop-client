package com.fozf.jsocc.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
@XmlRootElement
public class Course {
    private long id;
    private String courseTitle;
    private String courseDescription;
    private String courseCode;
    private String enrollmentKey;
    private String courseProgrammingLanguage;
    private Instructor instructor;
    private Date dateAdded;
    private Date dateModified;
    private Date enrollmentStartDate;
    private Date enrollmentEndDate;

    private List<Exercise> exercises = new ArrayList<>();

    public Course(){

    }

    public long getId() {
        return id;
    }

    public String getCourseProgrammingLanguage() {
        return courseProgrammingLanguage;
    }

    public void setCourseProgrammingLanguage(String courseProgrammingLanguage) {
        this.courseProgrammingLanguage = courseProgrammingLanguage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Date getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    public void setEnrollmentStartDate(Date enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    public Date getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public void setEnrollmentEndDate(Date enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getEnrollmentKey() {
        return enrollmentKey;
    }

    public void setEnrollmentKey(String enrollmentKey) {
        this.enrollmentKey = enrollmentKey;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }


}
