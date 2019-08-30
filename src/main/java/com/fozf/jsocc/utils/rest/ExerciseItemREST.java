package com.fozf.jsocc.utils.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.models.ExerciseItem;
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

public class ExerciseItemREST {
    private static final String REST_URI = "http://localhost:8080/api/v1/exerciseItem";
    private static Client client = ClientBuilder.newClient();

    // CREATE
    public static Response addExerciseItem(ExerciseItem exerciseItem){
        return client.target(REST_URI)
                .path("/create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(exerciseItem, MediaType.APPLICATION_JSON));
    }

    // READ
    public static ExerciseItem getExerciseById(int id){
        return client
                .target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(ExerciseItem.class);
    }

    public static List<Exercise> getExerciseItemByExerciseId(long exerciseId) {
        String response = client
                .target(REST_URI)
                .path("/findUsingCourseId/" + exerciseId)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Exercise> list = new ArrayList<>();

        try {
            list = mapper.readValue(response, new TypeReference<List<ExerciseItem>>() {
            });

        } catch (IOException e) {
            System.out.println("e");
        }

        return list;
    }


    // DELETE
    public static void deleteExerciseItemByExerId(int id) {
        client.target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .delete();
    };
}
