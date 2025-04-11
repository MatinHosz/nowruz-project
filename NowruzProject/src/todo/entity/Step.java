package todo.entity;

import db.Entity;

public class Step extends Entity {
    public static final int STEP_ENTITY_CODE = 2;
    public enum Status {
        NotStarted,
        Completed
    }

    public String title;
    public int taskRef;
    public Status status;

    public Step(String title, int taskRef) {
        this.title = title;
        this.taskRef = taskRef;
        this.status = Status.NotStarted;
    }

    @Override
    public int getEntityCode() { return STEP_ENTITY_CODE; }

    @Override
    public Step copy() {
        Step copyStep = new Step(title, taskRef);
        copyStep.status = status;
        return copyStep;
    }
}
