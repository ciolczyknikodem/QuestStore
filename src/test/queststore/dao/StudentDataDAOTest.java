package queststore.dao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import queststore.data.DbHelper;
import queststore.models.Group;
import queststore.models.StudentData;
import queststore.models.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentDataDAOTest {

    private StudentDataDAO studentDataDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;

    @BeforeEach
    void before() throws IOException {
        String testDbPath = "testDb.db";
        Files.deleteIfExists(new File(testDbPath).toPath());
        DbHelper.setDatabasePath(testDbPath);
        new DbHelper().createDatabase();
        this.studentDataDAO = new DbStudentDataDAO();
        this.userDAO = new DbUserDAO();
        this.groupDAO = new DbGroupDAO();
    }

    @Test
    void whenAddThenStudentDataIsAddedToDb() {
        User student = new User(1, "Student", "Student", "student@email.com", "student",
                "666555666", "Student");
        Group group1 = new Group(1,"Group1");
        StudentData expected = new StudentData(student.getId(), group1.getId(), "team", "pro",
                13, 30);
        this.userDAO.add(student);
        this.groupDAO.add(group1);
        this.studentDataDAO.add(expected);
        StudentData result = this.studentDataDAO.getStudentDataBy(student.getId());
        assertEquals(expected, result);
    }

    @Test
    void whenUpdateStudentDataThenItIsUpdatedInDb() {
        User student = new User(1, "Student", "Student", "student@email.com", "student",
                "666555666", "Student");
        Group group1 = new Group(1,"Group1");
        StudentData expected = new StudentData(student.getId(), group1.getId(), "team", "pro",
                13, 30);
        this.userDAO.add(student);
        this.groupDAO.add(group1);
        this.studentDataDAO.add(expected);
        expected.setTeamName("New");
        expected.setLevel("Level");
        expected.setBalance(234);
        expected.setExperience(100);
        this.studentDataDAO.updateStudentData(expected);
        StudentData result = this.studentDataDAO.getStudentDataBy(student.getId());
        assertEquals(expected, result);
    }

    @Test
    void whenGetAllStudentsDataThenReturnAllData() {
        User student1 = new User(1, "Student", "Student", "student@email.com", "student",
                "666555666", "Student");
        User student2 = new User(2, "Student2", "Student2", "student2@email.com", "student2",
                "666555626", "Student2");
        Group group1 = new Group(1,"Group1");
        StudentData studentData1 = new StudentData(student1.getId(), group1.getId(), "team1", "pro1",
                14, 31);
        StudentData studentData2 = new StudentData(student2.getId(), group1.getId(), "team2", "pro2",
                15, 32);
        this.userDAO.add(student1);
        this.userDAO.add(student2);
        this.groupDAO.add(group1);
        this.studentDataDAO.add(studentData1);
        this.studentDataDAO.add(studentData2);
        List<StudentData> studentsData = this.studentDataDAO.getAllStudents();
        assertEquals(studentData1, studentsData.get(0));
        assertEquals(studentData2, studentsData.get(1));
    }

    @Test
    void whenGetStudentsDataByTeamNameThenReturnCorrectData() {
        User student1 = new User(1, "Student", "Student", "student@email.com", "student",
                "666555666", "Student");
        User student2 = new User(2, "Student2", "Student2", "student2@email.com", "student2",
                "666555626", "Student2");
        Group group1 = new Group(1,"Group1");
        StudentData studentData1 = new StudentData(student1.getId(), group1.getId(), "team3", "pro1",
                14, 31);
        StudentData studentData2 = new StudentData(student2.getId(), group1.getId(), "team4", "pro2",
                15, 32);
        this.userDAO.add(student1);
        this.userDAO.add(student2);
        this.groupDAO.add(group1);
        this.studentDataDAO.add(studentData1);
        this.studentDataDAO.add(studentData2);
        List<StudentData> studentsData = this.studentDataDAO.getStudentsInSameTeamBy(studentData2.getTeamName());
        assertEquals(studentData2, studentsData.get(0));
    }
}
