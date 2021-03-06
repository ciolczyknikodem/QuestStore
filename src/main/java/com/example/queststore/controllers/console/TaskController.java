package com.example.queststore.controllers.console;

import com.example.queststore.dao.StudentTaskDAO;
import com.example.queststore.dao.TaskDAO;
import com.example.queststore.dao.UserDAO;
import com.example.queststore.data.contracts.TaskEntry;
import com.example.queststore.data.contracts.UserEntry;
import com.example.queststore.models.Task;
import com.example.queststore.models.User;
import com.example.queststore.views.console.TaskView;

import java.util.ArrayList;
import java.util.List;

public class TaskController {

    private final String BASIC_TASK = "b";
    private TaskDAO taskDAO;
    private UserDAO userDAO;
    private StudentTaskDAO studentTaskDAO;
    private StudentController studentController;
    private TaskView taskView;

    public TaskController(TaskDAO taskDAO, UserDAO userDAO, StudentTaskDAO studentTaskDAO, StudentController studentController,
                          TaskView taskView) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.studentTaskDAO = studentTaskDAO;
        this.studentController = studentController;
        this.taskView = taskView;
    }

    void addNewQuest() {
        String questName = taskView.getQuestNameInput();
        if (taskDAO.getByName(questName) != null) {
            taskView.displayQuestAlreadyExists();
        } else {
            int points = taskView.getQuestPointsInput();
            String description = taskView.getQuestDescriptionInput();
            String categoryInput = taskView.getQuestCategory();
            String category = categoryInput.equals(BASIC_TASK) ? TaskEntry.BASIC_TASK : TaskEntry.EXTRA_TASK;
            if (taskDAO.add(new Task(questName, points, description, category))) {
                taskView.displayQuestSuccessfullyAdded();
            } else {
                taskView.displayErrorAddingQuest();
            }
        }
    }

    void editQuest() {

        List<Task> quests = new ArrayList<>(taskDAO.getAll());
        taskView.displayEntriesNoInput(quests);
        if (quests.isEmpty()) {
            taskView.displayPressAnyKeyToContinueMessage();
            return;
        }
        String taskName = taskView.getQuestNameInput();
        if (taskDAO.getByName(taskName) != null) {
            updateQuest(taskDAO.getByName(taskName));
        } else {
            taskView.displayThereIsNoTaskWithThisName();
        }
    }

    private void updateQuest(Task task) {
        final String UPDATE_POINTS = "1";
        final String UPDATE_DESCRIPTION = "2";
        final String UPDATE_CATEGORY = "3";

        switch(taskView.getValueToUpdate(task)) {
            case UPDATE_POINTS:
                int points = taskView.askForPointsInput();
                task.setPoints(points);
                showEditResultMessage(taskDAO.update(task));
                break;
            case UPDATE_DESCRIPTION:
                String description = taskView.askForDescriptionInput();
                task.setDescription(description);
                showEditResultMessage(taskDAO.update(task));
                break;
            case UPDATE_CATEGORY:
                String categoryInput = taskView.getQuestCategory();
                String category = categoryInput.equals(BASIC_TASK) ? TaskEntry.BASIC_TASK : TaskEntry.EXTRA_TASK;
                task.setCategory(category);
                showEditResultMessage(taskDAO.update(task));
                break;
            default:
                taskView.displayWrongOptionMessage();
        }
    }

    private void showEditResultMessage(boolean isEdit) {
        if (isEdit) {
            taskView.displayValueHasBeenChanged();
        } else {
            taskView.displayErrorChangingTheValue();
        }
    }

    void markStudentAchievedQuest() {

        List<User> students = new ArrayList<>(userDAO.getAllByRole(UserEntry.STUDENT_ROLE));
        taskView.displayEntriesNoInput(students);
        if (students.isEmpty()) {
            taskView.displayPressAnyKeyToContinueMessage();
            return;
        }
        String studentLogin = taskView.getStudentLoginToMarkQuest();
        if (userDAO.getByLoginAndRole(studentLogin, UserEntry.STUDENT_ROLE) != null) {
            choseQuestToMark(studentLogin);
        } else {
            taskView.displayThereIsNoStudentWithThisLogin();
        }
    }

    private void choseQuestToMark(String studentLogin) {

        List<Task> quests = new ArrayList<>(taskDAO.getAll());
        taskView.displayEntriesNoInput(quests);
        if (quests.isEmpty()) {
            taskView.displayPressAnyKeyToContinueMessage();
            return;
        }
        String taskName = taskView.getTaskNameInput();
        if (taskDAO.getByName(taskName) != null) {
            Task task = taskDAO.getByName(taskName);
            User student = userDAO.getByLogin(studentLogin);
            boolean isAdded = studentTaskDAO.add(student.getId(), task.getId());
            if (isAdded) {
                taskView.displayTaskConnectionAdded();
                studentController.updateStudentBalance(student.getId(), task.getPoints());
                studentController.updateStudentExperienceAndLevel(student.getId(), task.getPoints());
            } else {
                taskView.displayErrorAddingTaskConnection();
            }
        } else {
            taskView.displayThereIsNoTaskWithThisName();
        }
    }
}
