package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> readFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable long id, @RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.editFaculty(id, faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable long id) {

        facultyService.deleteFaculty(id);
    }

    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> getAllFacultyByColor(@RequestParam String color) {
        return ResponseEntity.ok(facultyService.getAllFacultyByColor(color));
    }
    @GetMapping("/by-color-or-name")
    public ResponseEntity<Collection<Faculty>> getAllFacultyByColor(
            @RequestParam String color, @RequestParam String name) {
        return ResponseEntity.ok(facultyService.getAllFacultyByColorOrName(color, name));
    }
    @GetMapping("/{id}/students")
    public List<Student> getStudentsByFaculty(@PathVariable Long id) {
        return facultyService.getStudentsByFaculty(id);
    }

    @GetMapping("/longest-faculty-name")
    public String getLongestFacultyName() {
        return facultyService.getLongestFaculty();
    }
}
