package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    Map<Long, Student> students = new HashMap<>();
    private long lastIdStudent = 0;

    public Student createStudent(Student student) {
        student.setId(++lastIdStudent);
        students.put(lastIdStudent, student);
        return student;
    }
    public Student readStudent (Student student) {
        return students.get(student);
    }
    public Student updateStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Student student) {
        return students.remove(student);
    }

    public Collection<Student> getAllStudentsByAge(int age) {
        return students.values().stream().filter(it -> it.getAge() == age).collect(Collectors.toList());
    }

}
