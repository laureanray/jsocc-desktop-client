package com.fozf.jsocc.utils.rest;

import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.CourseTemplate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class CourseTemplateREST {

    private static final String REST_URI = "http://localhost:8080/api/v1/courseTemplate";
    private static Client client = ClientBuilder.newClient();

    public static List<CourseTemplate> findByProgrammingLanguage(String programmingLanguage){
        return client
                .target(REST_URI)
                .path("/findByProgrammingLanguage/" + programmingLanguage)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<CourseTemplate>>(){});
    }


}
