package org.example.services;
import org.example.exception.InvalidStudentDataException;
import org.example.model.Student;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService{
    private final List<Student> students = new ArrayList<>();
    private int idCounter = 1;

    public void AddStudent(String name, String email, LocalDate DOB, String phone){
        ValidateData(null, name, email, DOB, phone);
        Student student = new Student(idCounter++, name, email, DOB, phone);
        students.add(student);
        System.out.println("Student added successfully with ID: " + student.getStudentID());
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student searchById(int id) {
        return students.stream()
                .filter(s -> s.getStudentID() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Student> searchByName(String name) {
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void updateStudent(int id, String name, String email, LocalDate dob, String mobile) {
        Student student = searchById(id);
        if (student == null) {
            throw new InvalidStudentDataException("Student with ID " + id + " not found.");
        }
        ValidateData(id, name, email, dob, mobile);

        student.setName(name);
        student.setEmail(email);
        student.setDoB(dob);
        student.setPhoneNum(mobile);
        System.out.println("Student details updated successfully!");
    }

    public boolean deleteStudent(int id) {
        Student student = searchById(id);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
    }

    private void ValidateData(Integer id, String name, String email, LocalDate DOB, String phone){
        if (name == null || name.trim().isEmpty())
            throw new InvalidStudentDataException("Name is mandatory.");
        if (name.length() < 3 || name.length() > 50)
            throw new InvalidStudentDataException("Name must be between 3 and 50 characters.");
        if (!name.matches("^[a-zA-Z\\s]+$"))
            throw new InvalidStudentDataException("Name must contain only alphabets and spaces.");

        if (email == null || email.trim().isEmpty())
            throw new InvalidStudentDataException("Email is mandatory.");
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new InvalidStudentDataException("Invalid email format.");

        boolean emailExists = students.stream()
                .anyMatch(s -> s.getEmail().equalsIgnoreCase(email) && (id == null || s.getStudentID() != id));
        if (emailExists)
            throw new InvalidStudentDataException("Email address already exists.");

        if (DOB == null)
            throw new InvalidStudentDataException("Date of Birth is mandatory.");
        if (DOB.isAfter(LocalDate.now()))
            throw new InvalidStudentDataException("Date of Birth cannot be a future date.");

        int age = Period.between(DOB, LocalDate.now()).getYears();
        if (age < 5 || age > 60) throw new InvalidStudentDataException("Age must be between 5 and 60 years. Current calculated age: " + age);

        if (phone == null || phone.trim().isEmpty())
            throw new InvalidStudentDataException("Mobile number is mandatory.");
        if (!phone.startsWith("+92"))
            throw new InvalidStudentDataException("Mobile number must start with +92");
        if (!phone.matches("^\\+92\\d{10}$"))
            throw new InvalidStudentDataException("Mobile number must feature exactly 10 digits after +92 without letters or special characters.");
    }
}
