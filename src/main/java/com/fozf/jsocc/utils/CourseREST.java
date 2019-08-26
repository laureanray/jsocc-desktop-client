package com.fozf.jsocc.utils;

import com.fozf.jsocc.models.Course;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class CourseREST {
    private static final String REST_URI = "http://localhost:8080/api/v1/course";
    private static Client client = ClientBuilder.newClient();

    public static Course getCourseById(long id){
        return client
                .target(REST_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(Course.class);
    }

    public static Response createCourse(Course course){
        return client.target(REST_URI)
                .path("/create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(course, MediaType.APPLICATION_JSON));
    }

    public static List<Course> findByInstructorId(long id){
        return client
                .target(REST_URI)
                .path("/findByInstructorId/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Course>>(){});
    }






}
