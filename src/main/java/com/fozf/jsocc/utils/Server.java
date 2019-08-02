package com.fozf.jsocc.utils;

import com.fozf.jsocc.models.Student;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ConnectException;

public class Server {
    private static final String REST_URI = "http://localhost:8080/api/v1/students";
    private static Client client = ClientBuilder.newClient();

    public static boolean checkServer(){
        Response response = null;
        try {
            response = client
                    .target(REST_URI)
                    .path("")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
        } catch (Exception e){
            return false;
        }

        assert response != null;
        return response.getStatus() == 200;
    }
}
