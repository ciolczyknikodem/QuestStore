package views;
import java.util.*;
import models.*;


public class AdminView extends UserView {

    private Map<Integer, String> menu = new HashMap<>();
    private Scanner scanner;

    private void prepareAdminMenu() {
        menu.put(1, "Promote blank user.");
        menu.put(2, "Edit mentor profile and his groups.");
        menu.put(3, "Create new group and assign mentor.");
        menu.put(4, "Edit experience levels.");
        menu.put(5, "Show mentor profile and all his Codecoolers.");
        menu.put(6, "Log out.");
    }

    private void displayAdminMenu() {
        System.out.println("You are logged as Admin.");
        for (Integer option : menu.keySet()) {
            System.out.println(option + ". " + menu.get(option));
        }
    }

    public void handleAdminMenu() {
        prepareAdminMenu();
        displayAdminMenu();
    }

    public void displayBlankUsers(List<BlankUser> blankUsers) {
        System.out.println("\nBlank users list: ");
        for (BlankUser user : blankUsers) {
            System.out.println(user.toString());
        }
    }

    public String askForLogin() {
        scanner = new Scanner(System.in);

        System.out.print("\nEnter login profile to change: ");
        return scanner.next();
    }

    public Boolean getTypeOfPromotion() {
        String userChoose = "";
        String[] promoteOptions = {"m", "s"};
        scanner = new Scanner(System.in);

        System.out.println("Type 'm' if you want promote user to mentor or 's' to student");

        while (!Arrays.asList(promoteOptions).contains(userChoose.toLowerCase())) {
            userChoose = scanner.next();

            if (userChoose.equals("m")) {
                return true;
            }
            else if (userChoose.equals("s")) {
                return false;
            }
        }
        return null;
    }

    public void displayNoMentorMessage() {
        System.out.println("There is no mentor with this login!");
    }

    public void displayMentorProfile(Mentor mentor) {
        System.out.println(mentor);
    }

    public void displayMentors(List<Mentor> mentorContainer) {
        System.out.println("Mentors list: \n");

        if(!mentorContainer.isEmpty()) {
            for (Mentor mentor : mentorContainer) {
                System.out.println(mentor.toString());
            }
        } else {
            System.out.println("List is empty!");
        }
    }

    public void displayStudents(List<Student> studentContainer) {
        System.out.println("Available students to edit: \n");

        for (Student student : studentContainer) {
            System.out.println(student.toString());
        }
    }

    public int askForChangeInProfile(User profile) {
        System.out.println(profile.toString());
        System.out.println("What would you like to change in profile:" +
                            "\n1. Name" +
                            "\n2. Login" +
                            "\n3. Email" +
                            "\n4. Phone number");

        return askForOption();
    }

    public void displayGroupAdded() {
        System.out.println("Group has been added!");
    }

    public void displayGroupWithThisNameAlreadyExists() {
        System.out.println("Group with this name already exists!");
    }

    public String getGroupNameInput() {
        System.out.print("Enter group name: ");
        return getStringInput();
    }

    private String getStringInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayPressAnyKeyToContinueMessage() {
        System.out.print("\nPress any key to continue.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void displayHasBeenPromoted() {
        System.out.println("User has been promoted!");
        displayPressAnyKeyToContinueMessage();
    }

    public void displayUserNotExists() {
        System.out.println("User not exists!");
        displayPressAnyKeyToContinueMessage();
    }

    public void displayEmptyListMsg() {
        System.out.println("List is empty!");
        displayPressAnyKeyToContinueMessage();
    }

    public void displayUserDoesNotExist() {
        System.out.println("This user does not exist!");
        displayPressAnyKeyToContinueMessage();
    }

    public void displayWrongSignError() {
        System.out.println("You type wrong sign!");
    }

    public void displayLevelCreation() {
        System.out.println("Create new level:\nLevel name:");
    }

    public int askForThreshold() {
        System.out.println("Level threshold:");
        return askForOption();
    }
}
