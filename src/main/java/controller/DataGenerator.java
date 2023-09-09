package controller;

import model.Course;
import model.Group;
import model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    public void relateStudentsToCourses(List<Student> students, List<Course> courses) {
        for (Student student : students) {
            int maxCoursesForStudent = getRandomInteger(3);
            List<Course> studentCourses = new ArrayList<>();
            for (int count = 0; count <= maxCoursesForStudent; count++) {
                int randomCourseId = getRandomInteger(courses.size());
                Course randomCourse = courses.get(randomCourseId);
                if (!studentCourses.contains(randomCourse)) {
                    studentCourses.add(randomCourse);
                    courses.get(randomCourseId).setStudents(student);
                } else {
                    maxCoursesForStudent++;
                }
            }
            student.setCourses(studentCourses);
        }
    }
    
    public void relateStudentsToGroups(List<Student> students, List<Group> groups) {
        for (Student student : students) {
            int groupIndex = getRandomInteger(1, groups.size() - 1);
            student.setGroupId(groupIndex);
        }
    }
    
    public List<Student> getStudents() {
        List<Student> result = new ArrayList<>();
        int amount = 200;
        for (int studentId = 1; studentId <= amount; studentId++) {
            result.add(new Student(studentId, getRandomName(), getRandomLastName()));
        }
        return result;
    }
    
    private String getRandomLastName() {
        String[] lastNames = {"Linkoln", "Boll", "Drew", "Gonzales", "Bong", "Abrams", "Ovchinikov", "Vulconic",
                "Oridos", "Ford", "Robben", "Li", "Wolf", "Green", "Poulidisus", "Snejder", "Pratt", "McMilan",
                "Ulbrikh", "Trenka"};
        return lastNames[getRandomInteger(lastNames.length)];
    }
    
    private String getRandomName() {
        String[] names = {"Andrew", "Robert", "Mike", "Diego", "Fred", "Lenny", "Stephan", "Milko", "Roberto",
                "Frank", "Kirk", "Kasper", "Kate", "Lili", "Damistas", "Lory", "Menny", "Brian", "Bondie", "Dyondi"};
        return names[getRandomInteger(names.length)];
    }
    
    private int getRandomCapacity() {
        int[] capacity = {0, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        return capacity[getRandomInteger(capacity.length)];
    }
    
    public List<Course> getCourses() {
        List<Course> result = new ArrayList<>();
        result.add(new Course(1, "Mathematics", "Learn how to count apples"));
        result.add(new Course(2, "Physics", "Learn how apple fall"));
        result.add(new Course(3, "Biology", "Learn how apple grow up"));
        result.add(new Course(4, "Chemistry", "Learn how to grow perfect apple"));
        result.add(new Course(5, "Business", "Learn how to sell all apples"));
        result.add(new Course(6, "Geography", "Learn where apples are grow"));
        result.add(new Course(7, "Astronomy", "Learn about apples on other planets"));
        result.add(new Course(8, "Political", "Learn law about apples"));
        result.add(new Course(9, "History", "Learn how apples evolute"));
        result.add(new Course(10, "Literature", "Learn how apples describe in poems"));
        return result;
    }
    
    public List<Group> getGroups() {
        List<Group> result = new ArrayList<>();
        int amount = 10;
        for (int groupId = 1; groupId <= amount; groupId++) {
            result.add(new Group(groupId, groupNameGenerate()));
        }
        return result;
    }
    
    private String groupNameGenerate() {
        return String.format("%s%s-%d%d", getRandomLetter(), getRandomLetter(), getRandomInteger(10), getRandomInteger(10));
    }
    
    private String getRandomLetter() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.valueOf(alphabet.charAt(getRandomInteger(alphabet.length())));
    }
    
    private int getRandomInteger(int max) {
        return new Random().nextInt(max);
    }

    private int getRandomInteger(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }
}
