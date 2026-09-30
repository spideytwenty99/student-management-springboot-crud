package com.saurabh.crudSpringBootDemo.controller;



import com.saurabh.crudSpringBootDemo.dto.createStudentRequestDto;
import com.saurabh.crudSpringBootDemo.dto.createStudentResponseDto;
import com.saurabh.crudSpringBootDemo.dto.updateResponseStudentDto;
import com.saurabh.crudSpringBootDemo.dto.updateStudentRequestDto;
import com.saurabh.crudSpringBootDemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }


    //create student
    @PostMapping
    public /*Student*/ ResponseEntity<createStudentResponseDto> createStudent(@Valid @RequestBody createStudentRequestDto createStudentRequestDto){

        System.out.println("Inside Studnet Controller");
        createStudentResponseDto createdStudent=studentService.createStudent(createStudentRequestDto);
        System.out.println("Exiting Student Controller");
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(createdStudent);

    }

    //read one student
    @GetMapping("{id}")
    public ResponseEntity<createStudentResponseDto>getStudent(@PathVariable Long id){

        createStudentResponseDto studentResp=studentService.getStudent(id);

        return ResponseEntity.ok(studentResp);

    }


    //read all
    @GetMapping
    public ResponseEntity<List<createStudentResponseDto >>getAllStudent(){

        List<createStudentResponseDto> studentsList=studentService.getAllStudent();


        return ResponseEntity.ok(studentsList);


    }





    //update student
    @PutMapping
    public ResponseEntity<updateResponseStudentDto> updateStudent(/*@PathVariable*/ @RequestParam Long id, @RequestBody updateStudentRequestDto studentReq){

        updateResponseStudentDto studentResp=studentService.updateStudent(id,studentReq);

        return ResponseEntity.ok(studentResp) ;

    }


    //delete student

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam  Long id){
      studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam long id){
        studentService.deleteStudentSoftly(id);

        return ResponseEntity.noContent().build();
    }





}
