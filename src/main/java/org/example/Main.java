package org.example;

import org.example.exception.InvalidStudentDataException;
import org.example.model.Student;
import org.example.services.StudentService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final StudentService service = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while(true){
            System.out.println("\n\n--------Student Management System--------");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> AddStudent();
                    case "2" -> ViewAllStudents();
                    case "3" -> SearchById();
                    case "4" -> SearchByName();
                    case "5" -> UpdateStudentInfo();
                    case "6" -> DeleteStudent();
                    case "7" -> {
                        System.out.println("Exiting Application. Goodbye!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please select from 1 to 7.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void AddStudent(){
        String name = ValidateName();

        String email = ValidateEmail();

        LocalDate dob = parseDate();

        String phone = ValidatePhone();

        try{
            service.AddStudent(name, email, dob, phone);
        } catch (InvalidStudentDataException e) {
            System.out.println("Validation Failed: " + e.getMessage());
        }
    }
    private static void ViewAllStudents(){
        List<Student> students = service.getAllStudents();
        if(students.isEmpty())
            System.out.println("No Records Found!");
        else{
            printTableHeadings();
            students.forEach(Main::printStudentRow);
        }
    }

    private static void SearchById(){
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        try{
            int ID = Integer.parseInt(id);
            Student s = service.searchById(ID);
            if(s == null)
                System.out.println("No Student found with ID = " + ID);
            else{
                printTableHeadings();
                printStudentRow(s);
            }

        }catch (NumberFormatException e) {
            System.out.println("ID must be a numeric value.");
        }
    }

    private static void SearchByName(){
        System.out.print("Enter Student Name: ");
        String Name = scanner.nextLine();
        List<Student> students = service.searchByName(Name);
        if(students == null)
            System.out.println("No Student found with Name = " + Name);
        else{
            printTableHeadings();
            students.forEach(Main::printStudentRow);
        }
    }

    private static void UpdateStudentInfo() {
        System.out.print("Enter Student ID to Update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Student existing = service.searchById(id);
            if (existing == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.print("Enter New Name: ");
            String nameInput = scanner.nextLine();
            String name = nameInput.isEmpty() ? existing.getName() : nameInput;

            System.out.print("Enter New Email: ");
            String emailInput = scanner.nextLine();
            String email = emailInput.isEmpty() ? existing.getEmail() : emailInput;

            LocalDate dob = parseDate();

            System.out.print("Enter New Mobile Number: ");
            String phoneInput = scanner.nextLine();
            String phone = phoneInput.isEmpty() ? existing.getPhoneNum() : phoneInput;

            service.updateStudent(id, name, email, dob, phone);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a numeric value.");
        } catch (InvalidStudentDataException e) {
            System.out.println("Validation Failed: " + e.getMessage());
        }
    }

    private static void DeleteStudent(){
        System.out.print("Enter Student ID to Delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (service.deleteStudent(id)) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID must be a numeric value.");
        }
    }

    private static String ValidateName(){
        while (true) {
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine().trim();
            try {
                if (name.length() < 3 || name.length() > 50) {
                    throw new InvalidStudentDataException("Name must be 3 to 50 characters.");
                }

                if (!name.matches("[a-zA-Z ]+")) {
                    throw new InvalidStudentDataException("Name must contain only alphabets and spaces.");
                }
                return name;
            } catch (InvalidStudentDataException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String ValidateEmail(){
        while (true) {
            System.out.print("Enter Student Email: ");
            String email = scanner.nextLine().trim();
            try {
                if (email.trim().isEmpty())
                    throw new InvalidStudentDataException("Email is mandatory.");
                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
                    throw new InvalidStudentDataException("Invalid email format.");
                boolean emailExists = service.getAllStudents().stream()
                        .anyMatch(s -> s.getEmail().equalsIgnoreCase(email));
                if (emailExists)
                    throw new InvalidStudentDataException("Email address already exists.");
                return email;
            } catch (InvalidStudentDataException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String ValidatePhone(){
        while (true) {
            System.out.print("Enter Phone Number (Format: +923XXXXXXXXX) : ");
            String phone = scanner.nextLine().trim();
            try {
                if (phone.trim().isEmpty())
                    throw new InvalidStudentDataException("Mobile number is mandatory.");
                if (!phone.startsWith("+92"))
                    throw new InvalidStudentDataException("Mobile number must start with +92");
                if (!phone.matches("^\\+92\\d{10}$"))
                    throw new InvalidStudentDataException("Mobile number must feature exactly 10 digits after +92 without letters or special characters.");
                return phone;
            } catch (InvalidStudentDataException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static LocalDate parseDate() {
        while (true) {
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            try{
                LocalDate DOB = LocalDate.parse(scanner.nextLine().trim());
                try {
                    if (DOB.isAfter(LocalDate.now()))
                        throw new InvalidStudentDataException("Date of Birth cannot be a future date.");

                    int age = Period.between(DOB, LocalDate.now()).getYears();
                    if (age < 5 || age > 60)
                        throw new InvalidStudentDataException("Age must be between 5 and 60 years. Current calculated age: " + age);
                    return DOB;
                } catch (InvalidStudentDataException e) {
                    System.out.println("Error : "+ e.getMessage());
                }
            }catch (DateTimeParseException e) {
                System.out.println("Invalid Date format. Use YYYY-MM-DD (e.g., 2005-06-15)");
            }
        }
    }

    private static void printStudentRow(Student s) {
        System.out.printf("%-5d | %-20s | %-40s | %-12s | %-4d | %-15s\n",
                s.getStudentID(), s.getName(), s.getEmail(), s.getDoB(), Period.between(s.getDoB(), LocalDate.now()).getYears(), s.getPhoneNum());
    }

    private static void printTableHeadings() {
        System.out.printf("\n%-5s | %-20s | %-40s | %-12s | %-4s | %-15s\n", "ID", "Name", "Email", "DOB", "Age", "Mobile");
        System.out.println("-".repeat(130));
    }
}