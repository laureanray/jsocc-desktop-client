package com.fozf.jsocc.utils;

import com.fozf.jsocc.models.*;
import com.fozf.jsocc.utils.rest.AuthREST;
import com.fozf.jsocc.utils.rest.CourseTemplateREST;
import com.fozf.jsocc.utils.rest.ExerciseItemREST;
import com.fozf.jsocc.utils.rest.ExerciseREST;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

public class Testing {
    public static void main(String[] args) {
        List<CourseTemplate> response = CourseTemplateREST.findByProgrammingLanguage("Python");

        System.out.println(response.get(0).getCourseTitle());
    }
}
