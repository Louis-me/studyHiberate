package xyz.shi.domain;


import java.util.HashSet;
import java.util.Set;

public class Grade {
    private Integer Id;
    private String name;
    //持有 Student 引用的集合，来维护一对多关联关系
    private Set<Student> students = new HashSet<>();

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "Id=" + Id +
                ", name='" + name +
                '}';
    }
}