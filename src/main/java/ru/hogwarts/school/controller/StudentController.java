package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> readStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id, @RequestBody Student student) {
        Student updatedStudent = studentService.editStudent(id, student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {

        studentService.deleteStudent(id);
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getAllStudentsByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.getAllStudentsByAge(age));
    }

    @GetMapping("/by-age-between")
    public ResponseEntity<Collection<Student>> getAllStudentsByAgeBetween(
            @RequestParam int min,
            @RequestParam int max){
        return ResponseEntity.ok(studentService.getAllStudentsByAgeBetween(min, max));
    }
}
