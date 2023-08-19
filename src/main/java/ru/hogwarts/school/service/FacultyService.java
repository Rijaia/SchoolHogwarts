package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class.getName());
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        logger.info("Controller start");
        this.facultyRepository = facultyRepository;
        logger.info("Constructor End with {}", this.facultyRepository);
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Method addFaculty");
        return facultyRepository.save(faculty);
    }
    public Faculty findFaculty(long id) {
        logger.info("Method findFaculty start");
        return facultyRepository.findById(id).orElse(null);
    }
    public Faculty editFaculty(long id, Faculty faculty) {
        logger.info("Method editFaculty start");
        Faculty facultyFromDB = findFaculty(id);
        if (facultyFromDB==null) {
            return null;
        }
        facultyFromDB.setColor(faculty.getColor());
        facultyFromDB.setName(faculty.getName());
        logger.info("Method editFaculty end");
        return facultyRepository.save(facultyFromDB);
    }

    public void deleteFaculty(long id) {
        logger.info("Method deleteFaculty start");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFacultyByColor(String color) {
        logger.info("Method getAllFacultyByColor start");
        return  facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFacultyByColorOrName(String color, String name) {
        logger.info("Method getAllFacultyByColorOrName stat");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public List<Student> getStudentsByFaculty(Long id) {
        logger.info("Method getStudentsByFaculty start");
        return facultyRepository.findById(id).
                map(Faculty::getStudents).
                orElse(null);
    }
}
