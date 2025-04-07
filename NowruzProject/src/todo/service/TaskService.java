package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskService {

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


}