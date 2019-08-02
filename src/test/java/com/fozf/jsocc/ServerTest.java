package com.fozf.jsocc;

import com.fozf.jsocc.models.Student;
import com.fozf.jsocc.utils.StudentRest;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;


public class ServerTest extends ApplicationTest {
    @Test
    public void getRequest(){
        Student student = StudentRest.getStudentById(1);
        System.out.println(student.getFirstName());
    }

    @Test
    public void getAllStudents(){
        ArrayList<Student> students = StudentRest.getStudents();

        for(Student st : students){
            System.out.println(st.getFirstName());
        }
    }

}
