package com.fozf.jsocc.utils;

import com.fozf.jsocc.models.*;
import com.fozf.jsocc.utils.rest.AuthREST;
import com.fozf.jsocc.utils.rest.ExerciseItemREST;
import com.fozf.jsocc.utils.rest.ExerciseREST;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

public class Testing {
    public static void main(String[] args) {
        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.setItemDescription("asdas");
        exerciseItem.setItemTitle("Test");
        TestCase testCase = new TestCase();
        testCase.setInput("1");
        testCase.setOutput("2");
        exerciseItem.getTestCases().add(testCase);

        Response response = ExerciseItemREST.addExerciseItem(exerciseItem);
        System.out.println(response.getStatus());
    }
}
