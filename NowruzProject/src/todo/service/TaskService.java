package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskService {
    public static void saveTask(String title, String description, String dueDateStr) throws InvalidEntityException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = formatter.parse(dueDateStr);
            Task task  = new Task(title, description, dueDate);
            task.status = Task.Status.NotStarted;
            Database.add(task);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }
    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.Completed;
        Database.update(task);
    }
    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.NotStarted;
        Database.update(task);
    }
    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.status = Task.Status.InProgress;
        Database.update(task);
    }
    public static void updateTitle(int id, String title) throws InvalidEntityException {
        Task task = (Task) Database.get(id);
        task.title = title;
        Database.update(task);
    }
    public static void updateDescription(int id, String description) throws InvalidEntityException {
        Task task = (Task) Database.get(id);
        task.description = description;
        Database.update(task);
    }
    public static void dueDate(int id, String dueDateStr) throws InvalidEntityException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dueDate = formatter.parse(dueDateStr);
            Task task = (Task) Database.get(id);
            task.dueDate = dueDate;
            Database.update(task);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }
}