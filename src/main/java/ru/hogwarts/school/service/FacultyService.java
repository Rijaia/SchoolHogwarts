package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long lastIdFaculty = 0;
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++lastIdFaculty);
        faculties.put(lastIdFaculty, faculty);
        return faculties.get(faculty.getId());
    }
    public Faculty findFaculty(long id) {

        return faculties.get(id);
    }
    public Faculty editFaculty(long id, Faculty faculty) {
        if (faculties.containsKey(id)) {
            faculties.put(id,faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getAllFacultyByColor(String color) {
        return  faculties.values().stream().filter(it->it.getColor().equals(color)).collect(Collectors.toList());
    }
}
