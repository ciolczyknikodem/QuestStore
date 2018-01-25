package data.statements;

import data.contracts.StudentDataContract.StudentDataEntry;
import data.contracts.UserContract.UserEntry;
import models.StudentData;
import models.User;

public class StudentDataStatement {

    public static String getLevelQuery(int student_id) {
        return "SELECT " + StudentDataEntry.LEVEL +
                " FROM " + StudentDataEntry.TABLE_NAME +
                " WHERE " + student_id + " = " + StudentDataEntry.ID_USER + "; ";
    }

    public static String createStudentData(StudentData student, User user) {
        // For test, add same foreign key for team and Id user. After team implementation change it.
        return "INSERT INTO " + StudentDataEntry.TABLE_NAME + " ( " +
                StudentDataEntry.ID_USER + ", " +
                StudentDataEntry.ID_GROUP + ", " +
                StudentDataEntry.TEAM_NAME + ", " +
                StudentDataEntry.LEVEL + ", " +
                StudentDataEntry.BALANCE + ") " +
                "VALUES (" +
                // how i can get user id here... + ", "
                user.getId() + ", " +
                user.getId() + ", \'" +
                student.getTeamName() + "\', \'" +
                student.getLevel() + "\'," +
                student.getBalance() + "); ";
    }
}
