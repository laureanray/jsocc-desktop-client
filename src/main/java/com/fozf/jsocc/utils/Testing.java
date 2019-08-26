package com.fozf.jsocc.utils;

import com.fozf.jsocc.models.Instructor;
import com.fozf.jsocc.models.LoginForm;
import com.fozf.jsocc.models.Student;

public class Testing {
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("laureanray");
        loginForm.setPassword("P@$$w0rd");
        Student o = (Student) AuthREST.login(loginForm);
        System.out.println(o.getClass().getSimpleName());
    }
}
