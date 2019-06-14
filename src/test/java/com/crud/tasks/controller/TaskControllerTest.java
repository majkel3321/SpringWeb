package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task1", "Task1 content");
        try {
            when(taskController.getTask(anyLong())).thenReturn(taskDto);
        } catch (TaskNotFoundException e) {
            System.out.println(e);
        }

        //When&Then
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task1")))
                .andExpect(jsonPath("$.content", is("Task1 content")));

    }

    @Test
    public void shouldGetAllTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1L, "Task1", "Task1 content"));
        taskDtos.add(new TaskDto(2L, "Task2", "Task2 content"));
        taskDtos.add(new TaskDto(3L, "Task3", "Task3 content"));

        when(taskController.getTasks()).thenReturn(taskDtos);

        //When&Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].title", is("Task3")))
                .andExpect(jsonPath("$[2].content", is("Task3 content")));
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task1", "Task1 content");

        when(taskController.updateTask(any(TaskDto.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String taskDtoJson = gson.toJson(taskDto);

        //When&Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(taskDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task1")))
                .andExpect(jsonPath("$.content", is("Task1 content")));
    }

    @Test
    public void testCreateTask() throws Exception {
        //Given
        TaskDto taskDto1 = new TaskDto(1L, "Task1", "Task1 content");
        TaskDto taskDto2 = new TaskDto(2L, "Task2", "Task2 content");
        TaskDto taskDto3 = new TaskDto(3L, "Task3", "Task3 content");

        Gson gson = new Gson();
        List<String> taskDtoJsons = new ArrayList<>();
        taskDtoJsons.add(gson.toJson(taskDto1));
        taskDtoJsons.add(gson.toJson(taskDto2));
        taskDtoJsons.add(gson.toJson(taskDto3));

        //When&Then
        for (String task : taskDtoJsons) {
            mockMvc.perform(post("/v1/task/createTask")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(task))
                    .andExpect(status().isOk());
        }

        verify(taskController, times(3)).createTask(any(TaskDto.class));
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given
        TaskDto taskDto1 = new TaskDto(1L, "Task1", "Task1 content");
        Gson gson = new Gson();
        String taskDto1Json = gson.toJson(taskDto1);

        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(taskDto1Json))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(taskController, times(1)).deleteTask(anyLong());
    }
}
