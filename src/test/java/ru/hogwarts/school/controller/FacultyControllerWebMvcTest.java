package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    FacultyService facultyService;

    @Test
    void shouldCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");
        when(facultyService.addFaculty(faculty)).thenReturn(faculty);
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldGetFaculty() throws Exception {
        // given
        Faculty faculty = new Faculty(1L,"Gryffindor", "Red");
        when(facultyService.findFaculty(1L)).thenReturn(faculty);
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldUpdateFaculty() throws Exception {
        // given
        Faculty faculty = new Faculty(1L,"Gryffindor", "Red");
        when(facultyService.editFaculty(1L,faculty)).thenReturn(faculty);
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/faculty/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldDeleteFaculty() throws Exception {
        // given
        Long facultyId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/faculty/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldGetAllFacultyByColor() throws Exception {
        // given
        Faculty faculty1 = new Faculty(1L, "Gryffindor", "Red");
        Faculty faculty2 = new Faculty(2L, "Kjdhk","Red");
        when(facultyService.getAllFacultyByColor("Red"))
                .thenReturn(Arrays.asList(faculty1,faculty2));
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/color?color=Red")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.
                        writeValueAsString(Arrays.asList(faculty1,faculty2))))
                .andDo(print());
    }

    @Test
    void shouldGetStudentsByFaculty() throws Exception {
        // given
        Student student1 = new Student(1L, "Garry Potter", 23);
        Student student2 = new Student(2L, "Garry Potter", 23);

        when(facultyService.getStudentsByFaculty(1L))
                .thenReturn(Arrays.asList(student1, student2));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.
                        writeValueAsString(Arrays.asList(student1, student2))))
                .andDo(print());
    }
}
