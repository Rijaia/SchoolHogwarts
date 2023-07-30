package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }
    public Faculty editFaculty(long id, Faculty faculty) {
        Faculty facultyFromDB = findFaculty(id);
        if (facultyFromDB==null) {
            return null;
        }
        facultyFromDB.setColor(faculty.getColor());
        facultyFromDB.setName(faculty.getName());
        return facultyRepository.save(facultyFromDB);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFacultyByColor(String color) {
        return  facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFacultyByColorOrName(String color, String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public List<Student> getStudentsByFaculty(Long id) {
        return facultyRepository.findById(id).
                map(Faculty::getStudents).
                orElse(null);
    }
}
