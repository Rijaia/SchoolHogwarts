package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long lastIdStudent = 0;
    public Student addStudent(Student student) {
        student.setId(++lastIdStudent);
        students.put(lastIdStudent, student);
        return students.get(student.getId());
    }
    public Student findStudent(long id) {

        return students.get(id);
    }
    public Student editStudent(long id, Student student) {
        if (students.containsKey(id)) {
            students.put(id, student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> getAllStudentsByAge(int age) {
        return students.values().stream().filter(it -> it.getAge() == age).collect(Collectors.toList());
    }

}
