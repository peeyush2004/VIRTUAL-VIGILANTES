import java.util.*;

class Course {
    String courseName;
    double grade;

    Course(String courseName, double grade) {
        this.courseName = courseName;
        this.grade = grade;
    }
}

class Student {
    String studentId;
    String studentName;
    ArrayList<Course> courses;

    Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courses = new ArrayList<>();
    }

    void addOrUpdateCourse(String courseName, double grade) {
        for (Course c : courses) {
            if (c.courseName.equalsIgnoreCase(courseName)) {
                c.grade = grade;
                System.out.println("Updated grade for " + courseName);
                return;
            }
        }
        courses.add(new Course(courseName, grade));
        System.out.println("Added new course: " + courseName);
    }

    void displayGrades() {
        System.out.println("\nGrades for " + studentName + " (ID: " + studentId + "):");
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (Course c : courses) {
                System.out.println("Course: " + c.courseName + " | Grade: " + c.grade);
            }
            System.out.printf("Average Grade: %.2f\n", getAverageGrade());
        }
    }

    double getAverageGrade() {
        if (courses.isEmpty()) return 0;
        double sum = 0;
        for (Course c : courses) {
            sum += c.grade;
        }
        return sum / courses.size();
    }
}

public class StudentGradeTracker {
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== Student Grade Tracker ===");
            System.out.println("1. Add Student");
            System.out.println("2. Add/Edit Grade");
            System.out.println("3. Display Grades");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addOrEditGrade();
                    break;
                case 3:
                    displayStudentGrades();
                    break;
                case 4:
                    System.out.println("Exiting Program...");
                    break;
                default:
                    System.out.println("Invalid Choice. Please select again.");
            }
        } while (choice != 4);
    }

    static void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();
        if (students.containsKey(id)) {
            System.out.println("Student ID already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();
        students.put(id, new Student(id, name));
        System.out.println("Student added successfully.");
    }

    static void addOrEditGrade() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();
        Student s = students.get(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Name: ");
        String course = sc.nextLine().trim();
        System.out.print("Enter Grade (0-100): ");
        double grade = getDoubleInput();

        if (grade < 0 || grade > 100) {
            System.out.println("Invalid grade. Must be between 0 and 100.");
            return;
        }

        s.addOrUpdateCourse(course, grade);
    }

    static void displayStudentGrades() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();
        Student s = students.get(id);

        if (s == null) {
            System.out.println("Student not found.");
        } else {
            s.displayGrades();
        }
    }

    static int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine(); // consume leftover newline
        return value;
    }

    static double getDoubleInput() {
        while (!sc.hasNextDouble()) {
            System.out.print("Invalid input. Enter a number: ");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine(); // consume leftover newline
        return value;
    }
}
