package com.saurabh.crudSpringBootDemo.service;


import com.saurabh.crudSpringBootDemo.dto.createStudentRequestDto;
import com.saurabh.crudSpringBootDemo.dto.createStudentResponseDto;
import com.saurabh.crudSpringBootDemo.dto.updateResponseStudentDto;
import com.saurabh.crudSpringBootDemo.dto.updateStudentRequestDto;
import com.saurabh.crudSpringBootDemo.entitiy.Student;
import com.saurabh.crudSpringBootDemo.exception.DuplicateRsourceException;
import com.saurabh.crudSpringBootDemo.exception.ResourceNotFoundException;
import com.saurabh.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public createStudentResponseDto createStudent(createStudentRequestDto studentReqDto){

        Student student=mapToEntity(studentReqDto);

        if (emailExists(student)){
            throw new DuplicateRsourceException("Student with email "+ student.getEmail()+
                    " already exists");
        }

        Student studentResponse= studentRepository.save(student);

        return  mapToDto(studentResponse);
    }


    public createStudentResponseDto getStudent(Long id){
         Student studentResp= studentRepository.findById(id)
                 .orElseThrow(()->
                         new ResourceNotFoundException("Student with id " +id +" not found"));

         return mapToDto(studentResp);

    }

    public  List<createStudentResponseDto> getAllStudent(){
//       List<Student> studentList= studentRepository.findAll();
       List<Student> studentList= studentRepository.findByDeletedIsFalse();


       return studentList.stream()
               .map(this::mapToDto)
               .toList();
    }

    public updateResponseStudentDto updateStudent(Long id, updateStudentRequestDto studentReq){
        Student existingStudent= studentRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(()->  new ResourceNotFoundException("Student with id " +id +" not found"));


        existingStudent.setName(studentReq.getName());
        existingStudent.setRollNo(studentReq.getRollNo());
        existingStudent.setSubject(studentReq.getSubject());

        existingStudent.setAge(studentReq.getAge());
        existingStudent.setDeleted(false);
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student savedStudent= studentRepository.save(existingStudent);
        return mapToUpdateDto(savedStudent);
    }

    public void deleteStudent(Long id){
       Student studentToBeDeleted= studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + " not found"));


       studentRepository.delete(studentToBeDeleted);

    }

    public void deleteStudentSoftly(Long id){
        Student studentToBeDeleted= studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student with id " + id + " not found"));



        studentToBeDeleted.setDeleted(true);

        studentRepository.save(studentToBeDeleted);



    }

    private Student mapToEntity(createStudentRequestDto createStudentRequestDto){
        Student student=new Student();
        student.setName(createStudentRequestDto.getName());
        student.setAge(createStudentRequestDto.getAge());
        student.setEmail(createStudentRequestDto.getEmail());
        student.setRollNo(createStudentRequestDto.getRollNo());
        student.setSubject(createStudentRequestDto.getSubject());

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);
        return  student;
    }

    private createStudentResponseDto mapToDto(Student student){
        createStudentResponseDto responseDto= new createStudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());

        responseDto.setMessage("Student saved sucessfully");
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;

    }

    private updateResponseStudentDto mapToUpdateDto(Student student){
        updateResponseStudentDto responseDto= new updateResponseStudentDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());

        responseDto.setMessage("Student update sucessfully");
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }

    private boolean emailExists(Student student){
        //Indexing is the best for real projects and below isn't indexing!
      return  studentRepository.existsByEmail(student.getEmail());
    }


}
