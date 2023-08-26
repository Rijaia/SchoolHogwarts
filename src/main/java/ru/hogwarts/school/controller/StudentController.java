package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
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
    public ResponseEntity<Collection<Student>> getAllStudentsByAge(
            @RequestParam int age) {
        return ResponseEntity.ok(studentService.getAllStudentsByAge(age));
    }

    @GetMapping("/by-age-between")
    public ResponseEntity<Collection<Student>> getAllStudentsByAgeBetween(
            @RequestParam int min,
            @RequestParam int max){
        return ResponseEntity.ok(studentService.getAllStudentsByAgeBetween(min, max));
    }
    @GetMapping("/{id}/faculty")
    public Faculty getFacultyByStudent(@PathVariable Long id) {
        return studentService.getFacultyByStudent(id);
    }
    @PostMapping(value = "/{studentId}/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(
            @PathVariable Long studentId,
            @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/{studentId}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(
            @PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }
    @GetMapping(value = "/{studentId}/avatar-from-file")
    public void downloadAvatar(
            @PathVariable Long studentId,
            HttpServletResponse response
    ) throws IOException {
        Avatar avatar = avatarService.findAvatar(studentId);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }
    @GetMapping(value = "/total-number")
    public int getNumberStudents() {
        return studentService.getNumberStudents();
    }
    @GetMapping(value = "average-age")
    public int getAverageAgeStudents() {
        return studentService.getAverageAgeStudents();
    }
    @GetMapping(value = "last-five")
    public List<Student> getFiveLastStudents() {
        return studentService.getFiveLastStudents();
    }

    @GetMapping("//name-starting-with/{start}")
    public List<String> getStudentTheNameStartingWith(
            @PathVariable String start
    ) {
        return studentService.getStudentTheNameStartingWith(start);
    }

    @GetMapping("////name-starting-with/A")
    public List<String> getStudentByNameStartingWithA() {
        return studentService.getStudentByNameStartingWithA();
    }

    @GetMapping("avarage-age-stream")
    public Double getAverageAgeByString() {
        return studentService.getAverageAgeByString();
    }

}
