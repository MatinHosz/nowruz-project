package todo.entity;

import db.Entity;

public class Step extends Entity {
    public static final int STEP_ENTITY_CODE = 2;
    enum Status {
        NotStarted,
        Completed
    }

    public String title;
    public Status status;
    public int taskRef;

    public Step() {
        //TODO complete constructor
    }

    @Override
    public int getEntityCode() { return STEP_ENTITY_CODE; }
    @Override
    public Step copy() {
        //TODO complete copy method
    }
}
