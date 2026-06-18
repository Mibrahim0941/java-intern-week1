package org.example.services;
import org.example.exception.InvalidStudentDataException;
import org.example.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class StudentService{
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
        System.out.println("✔ Student details updated successfully!");
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

    }
}
