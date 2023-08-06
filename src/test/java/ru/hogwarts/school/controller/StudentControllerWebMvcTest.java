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
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AvatarService avatarService;
    @Test
    void shouldCreateStudent() throws Exception {
        //given
        Student student = new Student();
        student.setName("Harry Potter");
        student.setAge(25);
        when(studentService.addStudent(student)).thenReturn(student);
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }

    @Test
    void shouldGetStudent() throws Exception {
        // given
        Student student = new Student(1L,"Garry Potter",24);
        when(studentService.findStudent(1L)).thenReturn(student);
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student(studentId,"Garry Potter",24);

        when(studentService.editStudent(studentId,student)).thenReturn(student);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/student/"+ studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        // given
        Long studentId = 1L;
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/student/"+ studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldGetFacultyByStudent() throws Exception {
        //given
        Faculty faculty = new Faculty(1L,"Gryffindor","Red");
        when(studentService.getFacultyByStudent(1L)).thenReturn(faculty);
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/student/1/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andDo(print());
    }

    @Test
    void shouldGetAllStudentByAge() throws Exception {
        // given
        Student student1 = new Student(1L,"Garry Potter", 23);
        Student student2 = new Student(2L,"Germiona Greindger", 23);
        when(studentService.getAllStudentsByAge(23)).
                thenReturn(Arrays.asList(student1,student2));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/student/age/23")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.
                        writeValueAsString(Arrays.asList(student1,student2))))
                .andDo(print());
    }

}
