package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {

    @Autowired
    private DbService dbService;

    private List<Task> taskList;

    @Before
    public void createAndSaveTasks() {
        Task task1 = new Task(1L, "Task1", "Task1 content");
        Task task2 = new Task(1L, "Task2", "Task2 content");
        Task task3 = new Task(1L, "Task3", "Task3 content");

        dbService.saveTask(task1);
        dbService.saveTask(task2);
        dbService.saveTask(task3);

        taskList = dbService.getAllTasks();
    }

    @After
    public void cleanUp() {
        dbService.deleteTask(taskList.get(0).getId());
        dbService.deleteTask(taskList.get(1).getId());
        dbService.deleteTask(taskList.get(2).getId());
    }

    @Test
    public void saveTask() {
        //Given
        Task task = new Task(4L, "Task4", "Task4 content");

        //When
        dbService.saveTask(task);

        //Then
        assertEquals(4, dbService.getAllTasks().size());

        //CleanUp
        dbService.deleteTask(dbService.getAllTasks().get(3).getId());
    }

    @Test
    public void deleteTask() {
        //Given
        Task task = new Task(4L, "Task4", "Task4 content");
        dbService.saveTask(task);

        //When
        dbService.deleteTask(dbService.getAllTasks().get(3).getId());

        //Then
        assertEquals(3, dbService.getAllTasks().size());
    }

    @Test
    public void getAllTasks() {
        //Given&When
        List<Task> taskList = dbService.getAllTasks();

        //Then
        assertEquals(3, taskList.size());
        assertEquals("Task2", taskList.get(1).getTitle());
    }

    @Test
    public void getTask() {
        //Given&When
        Optional<Task> task = dbService.getTask(taskList.get(0).getId());

        //Then
        assertTrue(task.isPresent());
        assertEquals("Task1", task.get().getTitle());
    }
}