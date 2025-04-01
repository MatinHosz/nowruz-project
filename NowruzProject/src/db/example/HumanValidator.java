package db.example;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human))
            throw new IllegalArgumentException("Invalid input type. Expected Human");
        if (((Human) entity).age < 0)
            throw new InvalidEntityException("Invalid Human entity: Age cannot be negative");
        if (((Human) entity).name.isEmpty() || ((Human) entity).name == null)
            throw new InvalidEntityException("Invalid Human entity: Name cannot be null or empty");
    }

}
