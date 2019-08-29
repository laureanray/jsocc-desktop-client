package com.fozf.jsocc.utils.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.models.LoginForm;
import com.fozf.jsocc.models.Student;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseREST {
    private static final String REST_URI = "http://localhost:8080/api/v1/exercise";
    private static Client client = ClientBuilder.newClient();

    // CREATE
    public static Response addExercise(Exercise exercise){
        return client.target(REST_URI)
                .path("/create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(exercise, MediaType.APPLICATION_JSON));
    }

    // READ
    public static Exercise getExerciseById(int id){
        return client
                .target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(Exercise.class);
    }

    public static List<Exercise> getExercisesUsingCourseId(long courseId) {
        String response = client
                .target(REST_URI)
                .path("/findUsingCourseId/" + courseId)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Exercise> list = new ArrayList<>();

        try {
            list = mapper.readValue(response, new TypeReference<List<Exercise>>() {
            });

        } catch (IOException e) {
            System.out.println("e");
        }

        return list;
    }

    // UPDATE
    public static Response updateExercise(Exercise exercise){
        return client.target(REST_URI)
                .path("/update/" + exercise.getId())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(exercise, MediaType.APPLICATION_JSON));
    }


    // DELETE
    public static void deleteExerciseById(int id) {
        client.target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .delete();
    };
}
