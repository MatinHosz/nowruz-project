package todo.validator;

import db.Entity;
import db.Validator;
import todo.entity.Task;
import db.exception.InvalidEntityException;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task))
            throw new IllegalArgumentException("Input must be of type Task.");
        if (((Task) entity).title.isEmpty() || ((Task) entity).title == null)
            throw new IllegalArgumentException("Task title can not be null or empty.");
    }
}
