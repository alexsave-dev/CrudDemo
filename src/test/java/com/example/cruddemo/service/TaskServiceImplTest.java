package com.example.cruddemo.service;

import com.example.cruddemo.model.Task;
import com.example.cruddemo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceImplTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskServiceImpl taskService;

	@Test
	public void testGetAllTasks() {
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task(1L, "Task 1", "Descr", LocalDate.now(), false));
		tasks.add(new Task(2L, "Task 2", "Descr", LocalDate.now(), false));

		when(taskRepository.findAll()).thenReturn(tasks);

		List<Task> returnedTasks = taskService.getAllTasks();

		assertEquals(2, returnedTasks.size());
		assertEquals("Task 1", returnedTasks.get(0).getTitle());
		assertEquals("Task 2", returnedTasks.get(1).getTitle());
	}

	@Test
	public void testGetTaskById() {
		Task task = new Task(1L, "Task 1", "Descr", LocalDate.now(), false);

		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

		Task returnedTask = taskService.getTaskById(1L);

		assertEquals("Task 1", returnedTask.getTitle());
	}

	@Test
	public void testGetTaskById_NotFound() {
		when(taskRepository.findById(1L)).thenReturn(Optional.empty());

		Task returnedTask = taskService.getTaskById(1L);

		assertNull(returnedTask);
	}

	@Test
	public void testCreateTask() {
		Task task = new Task(1L, "Task 1", "Descr", LocalDate.now(), false);

		when(taskRepository.save(task)).thenReturn(task);

		Task returnedTask = taskService.createTask(task);

		assertEquals("Task 1", returnedTask.getTitle());
	}

	@Test
	public void testUpdateTask() {
		Task updatedTask = new Task(1L, "Task Updated", "Descr", LocalDate.now(), false);

		when(taskRepository.existsById(1L)).thenReturn(true);
		when(taskRepository.save(updatedTask)).thenReturn(updatedTask);

		Task returnedTask = taskService.updateTask(1L, updatedTask);

		assertEquals("Task Updated", returnedTask.getTitle());
	}

	@Test
	public void testUpdateTask_NotFound() {
		Task updatedTask = new Task(1L, "Task 1", "Descr", LocalDate.now(), false);

		when(taskRepository.existsById(1L)).thenReturn(false);

		Task returnedTask = taskService.updateTask(1L, updatedTask);

		assertNull(returnedTask);
	}

	@Test
	public void testDeleteTask() {
		doNothing().when(taskRepository).deleteById(1L);

		taskService.deleteTask(1L);

		verify(taskRepository, times(1)).deleteById(1L);
	}
}
