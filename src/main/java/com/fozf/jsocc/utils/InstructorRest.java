package com.fozf.jsocc.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fozf.jsocc.models.Instructor;
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

public class InstructorRest {
    private static final String REST_URI = "http://localhost:8080/api/v1/instructor";
    private static Client client = ClientBuilder.newClient();

    public static Student getInstructorById(int id){
        return client
                .target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(Student.class);
    }


    public static Response addInstructor(Instructor instructor){
        return client.target(REST_URI)
                .path("/register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(instructor, MediaType.APPLICATION_JSON));
    }

    public static Response login(LoginForm loginForm){
        return client.target(REST_URI)
                .path("/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(loginForm, MediaType.APPLICATION_JSON));
    }
}
