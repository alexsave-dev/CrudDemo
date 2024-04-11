package com.example.cruddemo.controller;

import com.example.cruddemo.model.Task;
import com.example.cruddemo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Descr", LocalDate.now(), false));
        tasks.add(new Task(2L, "Task 2", "Descr", LocalDate.now(), false));


        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task(1L, "Task 1", "Descr", LocalDate.now(), false);

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Task 1"));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task(1L, "Task 1", "Descr", LocalDate.now(), false);

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"title\": \"Task 1\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    public void testUpdateTask() throws Exception {
        LocalDate dateNow = LocalDate.now();
        Task updatedTask = new Task(1L, "Updated Task", "Descr", dateNow, false);

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        String updatedTaskJson = String.format("""
        {
            "id": 1,
            "title": "Updated Task",
            "description": "Descr",
            "dueDate": "%s",
            "completed": false
        }
        """, dateNow);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTaskJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Task"));

        verify(taskService, times(1)).updateTask(1L, updatedTask);
    }
    @Test
    public void testDeleteTask() throws Exception {
        Long taskId = 1L;

        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(taskId);
    }
}