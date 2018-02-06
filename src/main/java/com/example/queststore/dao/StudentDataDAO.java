package com.example.queststore.dao;

import com.example.queststore.models.StudentData;

import java.util.List;

public interface StudentDataDAO {

    StudentData getStudentDataBy(int student_id);
    List<StudentData> getAllStudents();
    List<StudentData> getStudentsInSameTeamBy(int student_id);
    boolean add(StudentData student);
    boolean updateStudentData(StudentData student);
}