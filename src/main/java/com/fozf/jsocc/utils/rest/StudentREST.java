package com.fozf.jsocc.utils.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fozf.jsocc.models.Course;
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

public class StudentREST {
    private static final String REST_URI = "http://localhost:8080/api/v1/students";
    private static Client client = ClientBuilder.newClient();



    public static Student getStudentById(int id){
        return client
                .target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(Student.class);
    }

    public static Student getStudentByUsername(String username){
        return client
                .target(REST_URI)
                .path("/find/" + username)
                .request(MediaType.APPLICATION_JSON)
                .get(Student.class);
    }

    public static ArrayList<Student> getStudents() {
        String response = client
                .target(REST_URI)
                .path("")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Student> list = new ArrayList<>();

        try {
            list = mapper.readValue(response, new TypeReference<List<Student>>() {
            });

        } catch(IOException e){
            System.out.println("e");
        }

        return list;
    }

    public static Response addStudent(Student student){
        return client.target(REST_URI)
                .path("/register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(student, MediaType.APPLICATION_JSON));
    }

    public static Response login(LoginForm loginForm){
        return client.target(REST_URI)
                .path("/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(loginForm, MediaType.APPLICATION_JSON));
    }

    public static Response enrollStudent(String studentUserName, long courseId){
        return client.target(REST_URI)
            .path("/enroll/" + studentUserName + "/" + String.valueOf(courseId))
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(studentUserName, MediaType.APPLICATION_JSON));
    }
}
