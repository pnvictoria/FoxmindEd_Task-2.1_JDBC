package view;

import controller.SchoolManager;
import exceptions.SchoolDAOException;
import model.Course;
import model.Group;
import model.Student;

import java.util.List;
import java.util.Map;

public class UserInterface {
    private static final String UNDER_LINE = "===============================";
    private SchoolManager manager;
    private ConsoleIO consoleIO;
    
    public UserInterface(SchoolManager manager, ConsoleIO consoleIO) {
        this.manager = manager;
        this.consoleIO = consoleIO;
    }

    public void runMenu() {
        boolean exit = false;
        while (!exit) {
            exit = getMenu();
        }
        consoleIO.close();
    }
    
    public boolean getMenu() {
        printMenu();
        System.out.print("Enter answer:");
        try {
            switch (consoleIO.getLetterInput()) {
            case "1":
                System.out.print("Please enter student count for search: ");
                int studentCount = consoleIO.getNumberInput();
                Map<Group, Integer> groups = manager.getGroupsByStudentCount(studentCount);
                System.out.println(generateGroupsString(groups));
                break;
            case "2":
                List<Course> courses = manager.getCourses();
                printCourses(courses);
                System.out.println(UNDER_LINE);
                System.out.print("Please enter course name for search: ");
                String courseName = consoleIO.getCourseNameInput(courses);
                List<Student> students = manager.getStudentsByCourseName(courseName);
                System.out.println(generateStudentsString(students));
                break;
            case "3":
                System.out.print("Please enter student first_name: ");
                String name = consoleIO.getStringInput();
                System.out.print("Please enter student last_name: ");
                String lastName = consoleIO.getStringInput();
                Student student = new Student();
                student.setFirstName(name);
                student.setLastName(lastName);
                manager.addNewStudent(student);
                System.out.println("New student " + name + " " + lastName + " added success.");
                break;
            case "4":
                List<Student> students1 = manager.getStudents();
                System.out.println(generateStudentsString(students1));
                System.out.print("Please enter student_id to delete:");
                int studentId = consoleIO.getNumberByMaxSizeInput(students1.size());
                manager.deleteStudent(studentId);
                System.out.println("Student deleted success.");
                break;
            case "5":
                int studentId1 = getSelectedStudentId();
                printCoursesByStudentId(studentId1);
                int courseId = getSelectedCourseId();
                manager.addStudentToCourse(studentId1, courseId);
                System.out.println("Student added to the course success.");
                break;
            case "6":
                int studentId2 = getSelectedStudentId();
                printCoursesByStudentId(studentId2);
                int courseId1 = getSelectedCourseId();
                manager.removeStudentFromCourse(studentId2, courseId1);
                System.out.println("Student removed from the course success.");
                break;
            case "0":
                return true;
            default:
                System.out.print("Select a letter from '1' to '6'");
                break;
            }
        } catch (SchoolDAOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public void printMenu() {
        System.out.println("");
        System.out.println("MENU:");
        System.out.println("1. Find all groups with less or equals student count");
        System.out.println("2. Find all students related to course with given name");
        System.out.println("3. Add new student");
        System.out.println("4. Delete student by STUDENT_ID");
        System.out.println("5. Add a student to the course");
        System.out.println("6. Remove the student from one of his or her courses");
        System.out.println("0. Exit");
    }

    private int getSelectedCourseId() throws SchoolDAOException {
        List<Course> courses = manager.getCourses();
        printCourses(courses);
        System.out.println(UNDER_LINE);
        System.out.print("Please enter course_id to add: ");
        return consoleIO.getNumberByMaxSizeInput(courses.size());
    }
    
    private int getSelectedStudentId() throws SchoolDAOException {
        List<Student> students = manager.getStudents();
        System.out.println(generateStudentsString(students));
        System.out.println(UNDER_LINE);
        System.out.print("Please enter student_id: ");
        return consoleIO.getNumberByMaxSizeInput(students.get(students.size() - 1).getId());
    }
    
    private void printCoursesByStudentId(int studentId) throws SchoolDAOException {
        List<Course> courses = manager.getCoursesByStudentId(studentId);
        printCourses(courses);
        System.out.println(UNDER_LINE);
    }
    
    private void printCourses(List<Course> courses) {
        for (Course course : courses) {
            if (course.getDescription() != null) {
                System.out.println(String.format("%d. %s: %s", course.getId(), course.getName(), course.getDescription()));
            } else {
                System.out.println(String.format("%d. %s", course.getId(), course.getName()));
            }
        }
    }
    
    private String generateGroupsString(Map<Group, Integer> groups) {
        StringBuilder sBuilder = new StringBuilder();
        for (Map.Entry<Group, Integer> entry : groups.entrySet())
            sBuilder.append(String.format("%s : %d\n", entry.getKey().getName(), entry.getValue()));
        return sBuilder.toString();
    }
    
    private String generateStudentsString(List<Student> students) {
        StringBuilder sBuilder = new StringBuilder();
        for (Student student : students) {
            sBuilder.append(String.format("%d. %s %s\n", student.getId(), student.getFirstName(), student.getLastName()));
        }
        return sBuilder.toString();
    }
}
