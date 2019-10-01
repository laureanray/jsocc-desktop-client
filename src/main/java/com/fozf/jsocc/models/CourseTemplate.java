package com.fozf.jsocc.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class CourseTemplate {
    private long id;
    private String courseTitle;
    private String courseTemplateCode;
    private String courseProgrammingLanguage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseTemplateCode() {
        return courseTemplateCode;
    }

    public void setCourseTemplateCode(String courseTemplateCode) {
        this.courseTemplateCode = courseTemplateCode;
    }

    public String getCourseProgrammingLanguage() {
        return courseProgrammingLanguage;
    }

    public void setCourseProgrammingLanguage(String courseProgrammingLanguage) {
        this.courseProgrammingLanguage = courseProgrammingLanguage;
    }
}
