package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> readFaculty(@PathVariable Faculty facultyId) {
        Faculty faculty = facultyService.readFaculty(facultyId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("{facultyId}")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty);
        if (updatedFaculty == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{facultyId}")
    public Faculty deleteFaculty(@PathVariable Faculty facultyId) {
        return facultyService.deleteFaculty(facultyId);
    }

    @GetMapping("color")
    public ResponseEntity<Collection<Faculty>> getAllFacultyByColor(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.getAllFacultyByColor(color));
    }
}
