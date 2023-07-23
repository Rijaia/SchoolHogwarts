package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    Map<Long, Faculty> faculties= new HashMap<>();
    private long lastIdFaculty = 0;
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastIdFaculty);
        faculties.put(lastIdFaculty, faculty);
        return faculty;
    }
    public Faculty readFaculty (Faculty faculty) {
        return faculties.get(faculty);
    }
    public Faculty updateFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(),faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(Faculty faculty) {
        return faculties.remove(faculty);
    }

    public Collection<Faculty> getAllFacultyByColor(String color) {
        return  faculties.values().stream().filter(it->it.getColor()==color).collect(Collectors.toList());
    }
}
