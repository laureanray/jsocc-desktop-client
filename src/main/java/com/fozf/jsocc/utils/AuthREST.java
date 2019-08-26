package com.fozf.jsocc.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fozf.jsocc.models.Instructor;
import com.fozf.jsocc.models.LoginForm;
import com.fozf.jsocc.models.Student;
import com.fozf.jsocc.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.GsonBuildConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthREST {
    private static final String REST_URI = "http://localhost:8080/api/v1/auth";
    private static Client client = ClientBuilder.newClient();
    public static User login(LoginForm loginForm){
        Response response =  client
                .target(REST_URI)
                .path("/signin")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(loginForm, MediaType.APPLICATION_JSON));

        Gson gson = new GsonBuilder().create();
        String json = response.readEntity(String.class);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        if(Boolean.valueOf(jsonObject.get("instructor").toString())){
            return gson.fromJson(json, Instructor.class);
        }else{
            return gson.fromJson(json, Student.class);
        }
    }
}
