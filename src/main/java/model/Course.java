package model;

import dao.HasId;

import java.util.ArrayList;
import java.util.List;

public class Course implements HasId<Integer> {
    private int id;
    private String name;
    private String description;
    private List<Student> students;

    public Course() {}

    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        students = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(Student student) {
        students.add(student);
    }
}

