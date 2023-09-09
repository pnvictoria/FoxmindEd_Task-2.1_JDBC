package view;

import controller.SchoolManager;
import exceptions.SchoolDAOException;
import model.Course;
import model.Student;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserInterfaceTest {
    private static final String MENU_ITEM_1 = "1";
    private static final String MENU_ITEM_2 = "2";
    private static final String MENU_ITEM_3 = "3";
    private static final String MENU_ITEM_4 = "4";
    private static final String MENU_ITEM_5 = "5";
    private static final String MENU_ITEM_6 = "6";

    private final SchoolManager mockManager = Mockito.mock(SchoolManager.class);
    private final ConsoleIO mockConsoleIO = Mockito.mock(ConsoleIO.class);
    private final UserInterface ui = new UserInterface(mockManager, mockConsoleIO);

    @Test
    public void getMenuWhenUserInputIs1() throws SchoolDAOException {
        String expected = "Please enter student count for search: ";

        Mockito.when(mockConsoleIO.getLetterInput()).thenReturn(MENU_ITEM_1);
        Mockito.when(mockConsoleIO.getNumberInput()).thenReturn(20);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        ui.getMenu();
        System.setOut(null);

        Mockito.verify(mockConsoleIO).getLetterInput();
        Mockito.verify(mockConsoleIO).getNumberInput();

        String actual = out.toString();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void getMenuWhenUserInputIs2() throws SchoolDAOException {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Mathematics", "Learn how to count apples"));
        courses.add(new Course(2, "Physics", "Learn how apple fall"));
        courses.add(new Course(3, "Biology", "Learn how apple grow up"));

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setFirstName("Boris");
        student.setLastName("Johnson");
        students.add(student);

        String expected = "Please enter course name for search: ";

        String course = "Biology";
        Mockito.when(mockConsoleIO.getLetterInput()).thenReturn(MENU_ITEM_2);
        Mockito.when(mockManager.getCourses()).thenReturn(courses);
        Mockito.when(mockConsoleIO.getCourseNameInput(courses)).thenReturn(course);
        Mockito.when(mockManager.getStudentsByCourseName(course)).thenReturn(students);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        ui.getMenu();
        System.setOut(null);

        Mockito.verify(mockConsoleIO).getLetterInput();
        Mockito.verify(mockManager).getCourses();
        Mockito.verify(mockConsoleIO).getCourseNameInput(courses);
        Mockito.verify(mockManager).getStudentsByCourseName(course);

        String actual = out.toString();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void getMenuWhenUserInputIs3() throws SchoolDAOException {
        Student student = new Student();
        String studentName = "Boris";
        String studentLastName = "Johnson";
        student.setFirstName(studentName);
        student.setLastName(studentLastName);

        Mockito.when(mockConsoleIO.getLetterInput()).thenReturn(MENU_ITEM_3);
        Mockito.when(mockConsoleIO.getStringInput()).thenReturn(studentName);
        Mockito.when(mockConsoleIO.getStringInput()).thenReturn(studentLastName);
        Mockito.when(mockManager.addNewStudent(student)).thenReturn(true);

        String expected = "Please enter student first_name: ";
        String expected1 = "Please enter student last_name: ";
        String expected2 = "New student ";
        String expected3 = " added success.";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        ui.getMenu();
        System.setOut(System.out);

        Mockito.verify(mockConsoleIO).getLetterInput();
        Mockito.verify(mockConsoleIO, Mockito.times(2)).getStringInput();
        Mockito.verify(mockManager).addNewStudent(Mockito.any());

        String actual = out.toString();
        assertTrue(actual.contains(expected));
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));
    }

    @Test
    public void getMenuWhenUserInputIs4() throws SchoolDAOException {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        int studentId = 1;
        student.setId(studentId);
        student.setFirstName("Boris");
        student.setLastName("Johnson");
        students.add(student);

        Mockito.when(mockConsoleIO.getLetterInput()).thenReturn(MENU_ITEM_4);
        Mockito.when(mockManager.getStudents()).thenReturn(students);
        Mockito.when(mockConsoleIO.getNumberByMaxSizeInput(students.size())).thenReturn(studentId);
        Mockito.when(mockManager.deleteStudent(studentId)).thenReturn(true);

        String expected = "Please enter student_id to delete:";
        String expected1 = "Student deleted success.";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        ui.getMenu();
        System.setOut(System.out);

        Mockito.verify(mockManager).getStudents();
        Mockito.verify(mockConsoleIO).getNumberByMaxSizeInput(Mockito.anyInt());
        Mockito.verify(mockManager).deleteStudent(Mockito.anyInt());

        String actual = out.toString();
        assertTrue(actual.contains(expected));
        assertTrue(actual.contains(expected1));
    }

    @Test
    public void getMenuWhenUserInputIs5() throws SchoolDAOException {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Mathematics", "Learn how to count apples"));
        courses.add(new Course(2, "Physics", "Learn how apple fall"));
        courses.add(new Course(3, "Biology", "Learn how apple grow up"));

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Boris");
        student.setLastName("Johnson");
        student.setCourses(courses);
        students.add(student);

        Student student1 = new Student();
        student1.setId(2);
        student1.setFirstName("Donald");
        student1.setLastName("Trump");
        student1.setCourses(courses);
        students.add(student1);

        Student student2 = new Student();
        student2.setId(3);
        student2.setFirstName("Angela");
        student2.setLastName("Merkel");
        student2.setCourses(courses);
        students.add(student2);

        int studentId = 1;
        int courseId = 1;

        Mockito.when(mockConsoleIO.getLetterInput()).thenReturn(MENU_ITEM_5);
        Mockito.when(mockManager.getStudents()).thenReturn(students);
        Mockito.when(mockConsoleIO.getNumberByMaxSizeInput(students.size())).thenReturn(studentId);
        Mockito.when(mockManager.getCoursesByStudentId(studentId)).thenReturn(courses);
        Mockito.when(mockManager.getCourses()).thenReturn(courses);
        Mockito.when(mockConsoleIO.getNumberByMaxSizeInput(students.size())).thenReturn(courseId);
        Mockito.when(mockManager.addStudentToCourse(studentId, courseId)).thenReturn(true);

        String expected = "Please enter course_id to add: ";
        String expected1 = "Please enter student_id: ";
        String expected2 = "===============================";
        String expected3 = "Student added to the course success.";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        ui.getMenu();
        System.setOut(System.out);

        Mockito.verify(mockConsoleIO).getLetterInput();
        Mockito.verify(mockManager).getStudents();
        Mockito.verify(mockConsoleIO, Mockito.times(2)).getNumberByMaxSizeInput(Mockito.anyInt());
        Mockito.verify(mockManager).getCoursesByStudentId(Mockito.anyInt());
        Mockito.verify(mockManager).getCourses();
        Mockito.verify(mockManager).addStudentToCourse(Mockito.anyInt(), Mockito.anyInt());

        String actual = out.toString();
        assertTrue(actual.contains(expected));
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));
    }

    @Test
    public void getMenuWhenUserInputIs6() throws SchoolDAOException {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Mathematics", "Learn how to count apples"));
        courses.add(new Course(2, "Physics", "Learn how apple fall"));
        courses.add(new Course(3, "Biology", "Learn how apple grow up"));

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Boris");
        student.setLastName("Johnson");
        student.setCourses(courses);
        students.add(student);

        Student student1 = new Student();
        student1.setId(2);
        student1.setFirstName("Donald");
        student1.setLastName("Trump");
        student1.setCourses(courses);
        students.add(student1);

        Student student2 = new Student();
        student2.setId(3);
        student2.setFirstName("Angela");
        student2.setLastName("Merkel");
        student2.setCourses(courses);
        students.add(student2);

        int studentId = 1;
        int courseId = 1;

        Mockito.when(mockConsoleIO.getLetterInput()).thenReturn(MENU_ITEM_6);
        Mockito.when(mockManager.getStudents()).thenReturn(students);
        Mockito.when(mockConsoleIO.getNumberByMaxSizeInput(students.size())).thenReturn(studentId);
        Mockito.when(mockManager.getCoursesByStudentId(studentId)).thenReturn(courses);
        Mockito.when(mockManager.getCourses()).thenReturn(courses);
        Mockito.when(mockConsoleIO.getNumberByMaxSizeInput(students.size())).thenReturn(courseId);
        Mockito.when(mockManager.removeStudentFromCourse(studentId, courseId)).thenReturn(true);

        String expected = "Please enter course_id to add: ";
        String expected1 = "Please enter student_id: ";
        String expected2 = "===============================";
        String expected3 = "Student removed from the course success.";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        ui.getMenu();
        System.setOut(System.out);

        Mockito.verify(mockConsoleIO).getLetterInput();
        Mockito.verify(mockManager).getStudents();
        Mockito.verify(mockConsoleIO, Mockito.times(2)).getNumberByMaxSizeInput(Mockito.anyInt());
        Mockito.verify(mockManager).getCoursesByStudentId(Mockito.anyInt());
        Mockito.verify(mockManager).getCourses();
        Mockito.verify(mockManager).removeStudentFromCourse(Mockito.anyInt(), Mockito.anyInt());

        String actual = out.toString();
        assertTrue(actual.contains(expected));
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
        assertTrue(actual.contains(expected3));
    }
}