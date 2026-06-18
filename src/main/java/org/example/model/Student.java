package org.example.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student{
    private int StudentID;
    protected String Name;
    protected String Email;
    protected LocalDate DoB;
    protected String PhoneNum;
}
