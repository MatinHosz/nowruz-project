package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskService {
    public static void saveTask(String title, String description, String dueDateStr) throws InvalidEntityException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = formatter.parse(dueDateStr);
            Task task = new Task(title, description, dueDate);
            task.status = Task.Status.NOT_STARTED;

            Database.add(task);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }

    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.COMPLETED;

        Database.getTasksSteps(taskId).forEach(step -> {
            try {
                StepService.setAsCompleted(step.id);
            } catch (InvalidEntityException e) {
                System.err.println("Failed to set step as completed: " + e.getMessage());
            }
        });

        Database.update(task);
    }

    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.NOT_STARTED;

        Database.update(task);
    }

    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.IN_PROGRESS;

        Database.update(task);
    }

    public static void updateTitle(int id, String newTitle) throws InvalidEntityException {
        Task task = (Task) Database.get(id);
        task.title = newTitle;

        Database.update(task);
    }

    public static void updateDescription(int id, String newDescription) throws InvalidEntityException {
        Task task = (Task) Database.get(id);
        task.description = newDescription;

        Database.update(task);
    }

    public static void updateDueDate(int id, String newDueDateStr) throws InvalidEntityException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = formatter.parse(newDueDateStr);
            Task task = (Task) Database.get(id);
            task.dueDate = dueDate;

            Database.update(task);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }

    public static Task getTaskById(int taskID) {
        if (Database.get(taskID) instanceof Task) {
            return (Task) Database.get(taskID);
        }
        throw new EntityNotFoundException("Task with taskID: " + taskID + " not found.");
    }

    public static ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> incompleteTasksList = new ArrayList<>();
        for (Entity entity : Database.getAll(Task.TASK_ENTITY_CODE)) {
            if (entity instanceof Task) {
                Task task = (Task) entity;
                if (task.status != Task.Status.COMPLETED)
                    incompleteTasksList.add(task);
            }
        }
        return incompleteTasksList;
    }
}