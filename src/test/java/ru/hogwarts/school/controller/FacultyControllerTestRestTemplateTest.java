package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FacultyRepository facultyRepository;
    @AfterEach
    public void resetDB(){
        facultyRepository.deleteAll();
    }

    @Test
    void shouldCreateFaculty() {
        // given
        Faculty faculty = new Faculty();
        faculty.setColor("Red");
        faculty.setName("Gryffindor");

        // when
        ResponseEntity<Faculty> response = restTemplate
                .postForEntity("/faculty", faculty, Faculty.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo(faculty.getName());
        Assertions.assertThat(response.getBody().getColor()).isEqualTo(faculty.getColor());

    }

    @Test
    void shouldGetFaculty() {
        // given

        Long facultyId = persistTestFaculty("Gryffindor", "Red").getId();
        // when
        ResponseEntity<Faculty> responseEntity = restTemplate
                .getForEntity("/faculty/{id}",Faculty.class, facultyId);
        // then
        Faculty faculty = responseEntity.getBody();
        Assertions.assertThat(faculty).isNotNull();
        Assertions.assertThat(faculty.getId()).isEqualTo(facultyId);
        Assertions.assertThat(faculty.getColor())
                .isEqualTo(persistTestFaculty("Gryffindor", "Red").getColor());
        Assertions.assertThat(faculty.getName())
                .isEqualTo(persistTestFaculty("Gryffindor", "Red").getName());

    }

    @Test
    void shouldRemoveFaculty() {
        // given
        Long facultyId = persistTestFaculty("Gryffindor", "Red").getId();

        // when
        restTemplate.delete("/faculty/{id}", facultyId);

        // then
        Assertions.assertThat(facultyRepository.findById(facultyId)).isEmpty();

    }



    private Faculty persistTestFaculty(String name, String color) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return facultyRepository.save(faculty);
    }

}
