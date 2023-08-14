package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }
    public Student editStudent(long id, Student student) {
        Student studentFromDB = findStudent(id);
        if (studentFromDB==null) {
            return null;
        }
        studentFromDB.setName(student.getName());
        studentFromDB.setAge(student.getAge());
        return studentRepository.save(studentFromDB);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }
    public Collection<Student> getAllStudentsByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudent(Long id) {
        return studentRepository.findById(id).
                map(Student::getFaculty).
                orElse(null);
    }
    public int getNumberStudents() {
        return studentRepository.getNumberStudents();
    }
    public int getAverageAgeStudents() {
        return studentRepository.getAverageAgeStudents();
    }
    public List<Student> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }
}
