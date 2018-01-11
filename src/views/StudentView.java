package views;
import models.Student;

import java.util.*;

public class StudentView {

    private Map<Integer, String> menu;
    private Scanner scanner = new Scanner(System.in);

    public void prepareStudentMenu() {
        menu.put(1, "See your wallet.");
        menu.put(2, "Buy artifact.");
        menu.put(3, "Buy artifact with your teammates.");
        menu.put(4, "See your level.");
        menu.put(5, "Log out.");
    }

    public void displayStudentMenu() {
        for (Integer option : menu.keySet()) {
            System.out.println(option + ". " + menu.get(option) + "\n");
        }
    }

}
