package com.fozf.jsocc.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Exercise {
    private long id;
    private Course course;
    private List<ExerciseItem> exerciseItems = new ArrayList<>();
    private String exerciseTitle;
    private String exerciseDescription;
    private Date exerciseDeadline;
    private Date dateAdded;
    private Date dateModified;
    private boolean isCompleted;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Exercise(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<ExerciseItem> getExerciseItems() {
        return exerciseItems;
    }

    public void setExerciseItems(List<ExerciseItem> exerciseItems) {
        this.exerciseItems = exerciseItems;
    }

    public String getExerciseTitle() {
        return exerciseTitle;
    }

    public void setExerciseTitle(String exerciseTitle) {
        this.exerciseTitle = exerciseTitle;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public Date getExerciseDeadline() {
        return exerciseDeadline;
    }

    public void setExerciseDeadline(Date exerciseDeadline) {
        this.exerciseDeadline = exerciseDeadline;
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
}