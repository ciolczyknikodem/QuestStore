package com.example.queststore.dao;

import com.example.queststore.data.DbHelper;
import com.example.queststore.data.PreparedStatementCreator;
import com.example.queststore.data.contracts.StudentDataEntry;
import com.example.queststore.data.statements.StudentDataStatement;
import com.example.queststore.models.StudentData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbStudentDataDAO extends DbHelper implements StudentDataDAO {

    private StudentDataStatement studentDataStatement = new StudentDataStatement();
    private PreparedStatementCreator psc = new PreparedStatementCreator();

    @Override
    public StudentData getStudentDataBy(int student_id) {

        String sqlStatement = studentDataStatement.getStudentDataById();
        StudentData studentData = null;
        try {
            PreparedStatement statement = getPreparedStatement(sqlStatement);
            statement.setInt(1, student_id);
            ResultSet resultSet = query(statement);
            while (resultSet.next()) {
                studentData = new StudentData(
                        resultSet.getInt(StudentDataEntry.ID_USER),
                        resultSet.getInt(StudentDataEntry.ID_GROUP),
                        resultSet.getString(StudentDataEntry.TEAM_NAME),
                        resultSet.getString(StudentDataEntry.LEVEL),
                        resultSet.getInt(StudentDataEntry.BALANCE),
                        resultSet.getInt(StudentDataEntry.EXPERIENCE)
                );
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
        return studentData;
    }

    @Override
    public List<StudentData> getAllStudents() {
        String sqlStatement = studentDataStatement.getAllStudents();
        List<StudentData> students = new ArrayList<>();
        try {
            PreparedStatement statement = getPreparedStatement(sqlStatement);
            ResultSet resultSet = query(statement);
            while (resultSet.next()) {
                students.add(new StudentData(
                        resultSet.getInt(StudentDataEntry.ID_USER),
                        resultSet.getInt(StudentDataEntry.ID_GROUP),
                        resultSet.getString(StudentDataEntry.TEAM_NAME),
                        resultSet.getString(StudentDataEntry.LEVEL),
                        resultSet.getInt(StudentDataEntry.BALANCE),
                        resultSet.getInt(StudentDataEntry.EXPERIENCE)));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
        return students;
    }

    public boolean add(StudentData student) {
        String sqlStatement = studentDataStatement.createStudentData();
        PreparedStatement statement = null;
        try {
            statement = getPreparedStatement(sqlStatement);
            statement.setInt(1, student.getId());
            statement.setInt(2, student.getGroupId());
            statement.setString(3, student.getTeamName());
            statement.setString(4, student.getLevel());
            statement.setInt(5, student.getBalance());
            statement.setInt(6, student.getExperience());
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return update(statement);
    }

    @Override
    public boolean updateStudentData(StudentData student) {
        String sqlStatement = studentDataStatement.updateStudentData();
        PreparedStatement statement = null;
        try {
            statement = getPreparedStatement(sqlStatement);
            statement.setInt(1, student.getGroupId());
            statement.setString(2, student.getTeamName());
            statement.setString(3, student.getLevel());
            statement.setInt(4, student.getBalance());
            statement.setInt(5, student.getExperience());
            statement.setInt(6, student.getId());
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return update(statement);
    }
}