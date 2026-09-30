package com.saurabh.crudSpringBootDemo.dto;

import jakarta.validation.constraints.*;

public class createStudentRequestDto {

    @NotBlank(message = "Name cannot be null or blank")
    @Size(min=2, max=50, message = "Student name must be within 2 to 50 character long")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value=18,message = "Studen tmust be alteaet 18")
    private int age;

    @NotBlank(message = "Studetn email cannot be blank")
    @Email(message = "Email must be valide")
    private String email;

    @NotNull(message = "Roll number is required")
    private Integer rollNo;

    @NotBlank(message = "Subject is required")
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
