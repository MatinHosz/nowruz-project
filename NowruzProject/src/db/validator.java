package db;

public interface validator {
    void validate(Entity entity) throws InvalidEntityException;
}
