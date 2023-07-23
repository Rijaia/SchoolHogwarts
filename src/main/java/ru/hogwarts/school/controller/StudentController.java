package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("Student")
    public ResponseEntity<Student> createStudent(Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(student);
    }

    @GetMapping("studentId")
    public ResponseEntity<Student> readStudent(Student studentId) {
        Student student = studentService.readStudent(studentId);
        if (student == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("studentId")
    public ResponseEntity<Student> updateStudent(Student studentId) {
        Student updatedStudent = studentService.updateStudent(studentId);
        if (updatedStudent == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("studentId")
    public Student deleteStudent(Student studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping("age")
    public ResponseEntity<Collection<Student>> getAllStudentsByAge(@PathVariable int age) {
        return ResponseEntity.ok(studentService.getAllStudentsByAge(age));
    }
}
