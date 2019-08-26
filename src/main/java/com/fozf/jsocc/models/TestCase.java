package com.fozf.jsocc.models;

public class TestCase {
    private long id;
    private ExerciseItem exerciseItem;
    private String input;
    private String output;

    public TestCase(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExerciseItem getExerciseItem() {
        return exerciseItem;
    }

    public void setExerciseItem(ExerciseItem exerciseItem) {
        this.exerciseItem = exerciseItem;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
