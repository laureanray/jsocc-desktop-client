package com.fozf.jsocc.utils;

import com.fozf.jsocc.models.*;
import com.fozf.jsocc.utils.rest.*;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

public class Testing {
    public static void main(String[] args) {
////        List<CourseTemplate> response = CourseTemplateREST.findByProgrammingLanguage("Python");
////
////        System.out.println(response.get(0).getCourseTitle());
//
//        Response response = StudentREST.enrollStudent("laureanray", 1);
//
//        System.out.println(response.getStatus());

        Student student = StudentREST.getStudentByUsername("laureanray");

//        System.out.println(response.getStatus());
        System.out.println(student.getCourses().size());
    }
}
