package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplateTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    StudentRepository studentRepository;
    @AfterEach
    public void resetDB(){
        studentRepository.deleteAll();
    }
}
