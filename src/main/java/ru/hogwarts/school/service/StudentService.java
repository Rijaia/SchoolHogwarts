package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

    public List<String> getStudentTheNameStartingWith(String start) {
         return studentRepository.findByNameIsStartingWith(start)
                .stream().map(Student::getName).map(String::toUpperCase)
                 .collect(Collectors.toList());
    }

    public List<String> getStudentByNameStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName).map(String::toUpperCase).sorted()
                .filter(it->it.startsWith("A")).collect(Collectors.toList());

    }

    public Double getAverageAgeByString() {
        return studentRepository.findAll().stream().mapToDouble(Student::getAge)
                .average().orElse(0.0);
    }

    public void threads() {
        List<Student> student = studentRepository.findAll();
        printName(student.get(0));
        printName(student.get(1));

        new Thread(()-> {
            printName(student.get(2));
            printName(student.get(3));

        }).start();

        new Thread(()-> {
            printName(student.get(4));
            printName(student.get(5));

        }).start();
    }
    public void threadsWithSynchronize() {
        List<Student> student = studentRepository.findAll();
        printNameSynchronized(student.get(0));
        printNameSynchronized(student.get(1));

        new Thread(()-> {
            printNameSynchronized(student.get(2));
            printNameSynchronized(student.get(3));

        }).start();

        new Thread(()-> {
            printNameSynchronized(student.get(4));
            printNameSynchronized(student.get(5));

        }).start();
    }



    private void printName(Student student) {
        System.out.println(student.getName());
    }
    private synchronized void printNameSynchronized(Student student) {
        System.out.println(student.getName());
    }
}
