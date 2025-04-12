package todo.validator;

import db.Entity;
import db.Validator;
import db.Database;
import todo.entity.Step;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws IllegalArgumentException {
        if (!(entity instanceof Step))
            throw new IllegalArgumentException("Input must be of type Step.");
        if (((Step) entity).title.isEmpty() || ((Step) entity).title == null)
            throw new IllegalArgumentException("Step title can not be null or empty.");
        Database.get(((Step) entity).taskRef); // Check does any Task object with taskref id exist in database, if not,
        // it will throw exception
    }
}