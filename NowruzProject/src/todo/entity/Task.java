package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public static final int TASK_ENTITY_CODE = 1;
    public enum Status{
        NotStarted,
        InProgress,
        Completed
    }

    public String title;
    public String description;
    private Date creationDate;
    private Date lastModificationDate;
    public Date dueDate;
    public Status status;

    public Task(String title, String description,) {
        //TODO complete constructor
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
    @Override
    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }
    @Override
    public Task copy() {
        //TODO complete copy method
        Task copyTask = new Task()
    }
}
