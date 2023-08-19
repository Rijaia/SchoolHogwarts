package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class.getName());
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        logger.info("Constructor StudentService start");
        this.studentRepository = studentRepository;
        logger.info("Constructor StudentService end with {}", this.studentRepository);
    }

    public Student addStudent(Student student) {
        logger.info("Method addStudent start");
        return studentRepository.save(student);
    }
    public Student findStudent(long id) {
        logger.info("Method findStudent start");
        return studentRepository.findById(id).orElse(null);
    }
    public Student editStudent(long id, Student student) {
        logger.info("Method editStudent start");
        Student studentFromDB = findStudent(id);
        if (studentFromDB==null) {
            return null;
        }
        studentFromDB.setName(student.getName());
        studentFromDB.setAge(student.getAge());
        logger.info("Method editStudent end");
        return studentRepository.save(studentFromDB);
    }

    public void deleteStudent(long id) {
        logger.info("Method deleteStudent start");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudentsByAge(int age) {
        logger.info("Method getAllStudentsByAge start");
        return studentRepository.findByAge(age);
    }
    public Collection<Student> getAllStudentsByAgeBetween(int min, int max) {
        logger.info("Method getAllStudentsByAgeBetween start");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudent(Long id) {
        logger.info("Method getFacultyByStudent start");
        return studentRepository.findById(id).
                map(Student::getFaculty).
                orElse(null);
    }
    public int getNumberStudents() {
        logger.info("Method getNumberStudents start");
        return studentRepository.getNumberStudents();
    }
    public int getAverageAgeStudents() {
        logger.info("Method getAverageAgeStudents stat");
        return studentRepository.getAverageAgeStudents();
    }
    public List<Student> getFiveLastStudents() {
        logger.info("Method getFiveLastStudents start");
        return studentRepository.getFiveLastStudents();
    }
}
