package controllers;

import java.util.List;

import views.AdminView;
import models.*;
import dao.*;

public class AdminController {

    private AdminView view = new AdminView();
    private BlankUserDAO blankUserDAO = new BlankUserDAO();
    private GroupDAO groupDAO = new GroupDAO();
    private MentorDAO mentorDAO = new MentorDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private LevelThresholdDAO levelDAO = new LevelThresholdDAO();

    public void start() {

        int option;
        boolean isRunning = true;

        while (isRunning) {
            view.clearConsole();
            view.handleAdminMenu();
            option = view.askForOption();

            if (option == 1) {
                handlePromoteBlankUser();
            } else if (option == 2) {
                handleCreatingGroup();
            } else if (option == 3) {
                handleEditProfile();
            } else if (option == 4) {
                handleShowingMentorProfile();
            } else if (option == 5) {
                handleCreateLevel();
            } else if (option == 6) {
                isRunning = false;
            }
        }
    }

    private void handlePromoteBlankUser() {

        if (blankUserDAO.getBlankUsers().size() > 0) {
            view.displayBlankUsers(blankUserDAO.getBlankUsers());
            String login = view.askForLogin();
            BlankUser user = blankUserDAO.getBlankUserBy(login);

            if (user != null) {
                promote(user);
            } else {
                view.displayUserDoesNotExist();
            }
        } else {
            view.displayEmptyListMsg();
        }
    }

    private void promote(BlankUser user) {

        boolean isPromoteToMentor = view.getTypeOfPromotion();

        if (isPromoteToMentor) {
            Mentor mentor = new Mentor( user.getName(),user.getLogin(),user.getPassword(),
                    user.getEmail(), user.getPhoneNumber() );
            mentorDAO.addMentor(mentor);
        } else {
            Student student = new Student( user.getName(),user.getLogin(),user.getPassword(),
                    user.getEmail(),user.getPhoneNumber(),0 );
            studentDAO.addStudent(student);
        }

        if (blankUserDAO.removeBlankUser(user.getLogin())) {
            view.displayHasBeenPromoted();
        } else {
            view.displayUserNotExists();
        }
    }

    private void handleCreatingGroup() {

        String name = view.getGroupNameInput();
        Group group = new Group(name);
        if (groupDAO.addGroup(group)) {
            view.displayGroupAdded();
            if (mentorDAO.getMentors().size() > 0) {
                addGroupToMentor(group);
            } else {
                view.displayThereIsNoMentorsMessage();
            }
        } else {
            view.displayGroupWithThisNameAlreadyExists();
        }
    }

    private void addGroupToMentor(Group group) {
        view.displayMentors(mentorDAO.getMentors());
        String mentorLogin = view.getMentorLoginToAssignGroup();
        if (mentorDAO.getMentorBy(mentorLogin) != null) {
            mentorDAO.getMentorBy(mentorLogin).addGroup(group.getID());
            view.displayMentorAssignedToThisGroup();
        } else {
            view.displayThereIsNoMentorWithThisLogin();
        }
    }

    private void handleShowingMentorProfile() {

        view.displayMentors(mentorDAO.getMentors());
        if (mentorDAO.getMentors().isEmpty()) {
            view.displayPressAnyKeyToContinueMessage();
            return;
        }
        String login = view.getMentorLoginToShow();
        Mentor mentor = mentorDAO.getMentorBy(login);

        if (mentor != null) {
            view.displayMentorProfile(mentor);
            showMentorGroups(mentor);
        } else {
            view.displayNoMentorMessage();
        }
    }

    private void showMentorGroups(Mentor mentor) {

        List<Integer> groupsIDs = mentor.getGroupsIDs();
        if (groupsIDs.isEmpty()) {
            view.displayMentorHasNoGroupsAssigned();
        }
    }

    private void handleEditProfile() {

        final String QUIT_OPTION = "q";

        view.displayMentors(mentorDAO.getMentors());
        if (mentorDAO.getMentors().isEmpty()) {
            view.displayPressAnyKeyToContinueMessage();
            return;
        }
        String login = view.getMentorLoginToEdit();
        if (login.equals(QUIT_OPTION)) return;
        Mentor profileToEdit = mentorDAO.getMentorBy(login);
        if (profileToEdit != null) {
            updateProfileAttribute(profileToEdit);
            // Change class of mentor and add him a group
            mentorDAO.saveAllMentors();
        } else {
            view.displayThereIsNoMentorWithThisLogin();
        }
    }

    private void updateProfileAttribute(Mentor profile) {

        int toChange = view.askForChangeInProfile(profile);
        if (toChange == 1) {
            String name = view.askForNewValue();
            profile.setName(name);
            view.displayValueHasBeenChanged();
        } else if (toChange == 2) {
            String login = view.askForNewValue();
            profile.setLogin(login);
            view.displayValueHasBeenChanged();
        } else if (toChange == 3) {
            String email = view.askForNewValue();
            profile.setEmail(email);
            view.displayValueHasBeenChanged();
        } else if (toChange == 4) {
            String phoneNumber = view.askForNewValue();
            profile.setPhoneNumber(phoneNumber);
            view.displayValueHasBeenChanged();
        } else {
            view.displayWrongSignError();
        }
    }

    private void handleCreateLevel() {

        String levelName = view.getLevelNameInput();
        int value = view.getLevelValueInput();

        levelDAO.setThreshold(levelName, value);
        view.displayLevelSetMessage();

    }
}
