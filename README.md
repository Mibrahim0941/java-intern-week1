# Student Management Console Application

A modular Java based terminal application designed to maintain, validate, and manage student registration records.

## Features
- **Dynamic ID Allocation**: Self-incrementing tracking IDs.
- **RegEx Based Input Validation**: Checks input fields for data types, lengths, age limit , and phone formats.
- **Fault Interception with Defined Exceptions**: Handled seamlessly via custom `InvalidStudentDataException` processing runtime bounds safely.
- **Lombok Integration**: Decouples data templates from boilerplate code.

## Sample Usage

### Adding a Record:
```text
--------Student Management System--------
1. Add Student
2. View All Students
3. Search Student by ID
4. Search Student by Name
5. Update Student
6. Delete Student
7. Exit
Select an option: 1
Enter Name: Muhammad Ibrahim
Enter Email: mi094131@gmail.com
Enter Date of Birth (YYYY-MM-DD): 2005-03-21
Enter Mobile Number (e.g., +923XXXXXXXXX): +923174289674
Student added successfully with ID: 1


--------Student Management System--------
1. Add Student
2. View All Students
3. Search Student by ID
4. Search Student by Name
5. Update Student
6. Delete Student
7. Exit
Select an option: 2

ID    | Name                 | Email                     | DOB          | Age  | Mobile         
------------------------------------------------------------------------------------------
1     | Muhammad Ibrahim     | mi094131@gmail.com        | 2005-03-21   | 21   | +923174289674
```
### Modifying a Record
```text
--------Student Management System--------
1. Add Student
2. View All Students
3. Search Student by ID
4. Search Student by Name
5. Update Student
6. Delete Student
7. Exit
Select an option: 5
Enter Student ID to Update: 1
Enter New Name: 
Enter New Email: 
Enter Date of Birth (YYYY-MM-DD): 2005-03-22
Enter New Mobile Number: +923174876594
Student details updated successfully!


--------Student Management System--------
1. Add Student
2. View All Students
3. Search Student by ID
4. Search Student by Name
5. Update Student
6. Delete Student
7. Exit
Select an option: 2

ID    | Name                 | Email                     | DOB          | Age  | Mobile         
------------------------------------------------------------------------------------------
1     | ibrahim              | mi094131@gmail.com        | 2005-03-22   | 21   | +923174876594
```

###Search by ID and Name
```text
--------Student Management System--------
1. Add Student
2. View All Students
3. Search Student by ID
4. Search Student by Name
5. Update Student
6. Delete Student
7. Exit
Select an option: 3
Enter Student ID: 1

ID    | Name                 | Email                     | DOB          | Age  | Mobile         
------------------------------------------------------------------------------------------
1     | ibrahim              | mi094131@gmail.com        | 2005-03-22   | 21   | +923174876594


--------Student Management System--------
1. Add Student
2. View All Students
3. Search Student by ID
4. Search Student by Name
5. Update Student
6. Delete Student
7. Exit
Select an option: 4
Enter Student Name: ib

ID    | Name                 | Email                     | DOB          | Age  | Mobile         
------------------------------------------------------------------------------------------
1     | ibrahim              | mi094131@gmail.com        | 2005-03-22   | 21   | +923174876594  
```
