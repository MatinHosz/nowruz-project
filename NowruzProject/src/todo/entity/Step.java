package todo.entity;

import db.Entity;

public class Step extends Entity {
    public static final int STEP_ENTITY_CODE = 2;
    public enum Status {
        NotStarted,
        Completed
    }

    public String title;
    public Status status;
    public int taskRef;

    public Step(String title, int taskRef) {
        this.title = title;
        this.taskRef = taskRef;
    }

    @Override
    public int getEntityCode() { return STEP_ENTITY_CODE; }
    @Override
    public Step copy() {
        //TODO complete copy method
    }
}
