import java.io.*;
import java.util.*;

abstract class Employee {
    protected String name;
    protected double salary;
    protected String designation;

    public Employee(String name, double salary, String designation) {
        this.name = name;
        this.salary = salary;
        this.designation = designation;
    }

    public abstract double calculateMonthlyPay();

    public String getDetails() {
        return name + " | " + designation + " | Salary: Rs." + salary;
    }

    public String toFileString() {
        return getClass().getSimpleName() + "," + name + "," + salary + "," + designation;
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name, double salary, String designation) {
        super(name, salary, designation);
    }

    @Override
    public double calculateMonthlyPay() {
        double bonus = 0.10 * salary;
        double deduction = 0.05 * salary;
        return salary + bonus - deduction;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name, double salary, String designation) {
        super(name, salary, designation);
    }

    @Override
    public double calculateMonthlyPay() {
        double bonus = 0.05 * salary;
        double deduction = 0.02 * salary;
        return salary + bonus - deduction;
    }
}

class PayrollSystem {
    private ArrayList<Employee> employees;
    private final String FILE_NAME = "employees.txt";

    public PayrollSystem() {
        employees = new ArrayList<>();
        loadFromFile();
    }

    public void addEmployee(Employee emp) {
        employees.add(emp);
        saveToFile();
    }

    public void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee emp : employees) {
            System.out.println(emp.getDetails() + " | Monthly Pay: Rs." + emp.calculateMonthlyPay());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                writer.println(emp.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        employees.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;
                String type = parts[0];
                String name = parts[1];
                double salary = Double.parseDouble(parts[2]);
                String designation = parts[3];

                if (type.equals("FullTimeEmployee")) {
                    employees.add(new FullTimeEmployee(name, salary, designation));
                } else if (type.equals("PartTimeEmployee")) {
                    employees.add(new PartTimeEmployee(name, salary, designation));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous data found.");
        }
    }
}

public class emppayroll {
    public static void main(String[] args) {
        PayrollSystem system = new PayrollSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Employee Payroll System ===");
            System.out.println("1. Add Full-Time Employee");
            System.out.println("2. Add Part-Time Employee");
            System.out.println("3. Display All Employees");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                case 2:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    sc.nextLine(); 
                    System.out.print("Enter Designation: ");
                    String desig = sc.nextLine();

                    if (choice == 1) {
                        system.addEmployee(new FullTimeEmployee(name, salary, desig));
                    } else {
                        system.addEmployee(new PartTimeEmployee(name, salary, desig));
                    }
                    break;

                case 3:
                    system.displayAllEmployees();
                    break;

                case 0:
                    System.out.println("Exiting system...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
