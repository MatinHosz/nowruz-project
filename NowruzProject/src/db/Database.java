package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    public static int currentEntityID = 1; // Next ID to be assigned to a new entity

    private Database() {
    } // Private constructor to prevent instantiation

    public static void add(Entity e) throws InvalidEntityException {
        if (validators.get(e.getEntityCode()) != null) {
            Validator validator = validators.get(e.getEntityCode());
            validator.validate(e);
        }

        if (e instanceof Trackable) {
            ((Trackable) e).setCreationDate(new Date());
            ((Trackable) e).setLastModificationDate(new Date());
        }

        e.id = currentEntityID++;
        entities.add(e.copy());
    }

    public static Entity get(int id) {
        for (Entity e : entities) {
            if (e.id == id) {
                return e.copy();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        for (Entity entity : entities) {
            if (entity.id == id) {
                entities.remove(id);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity e) throws InvalidEntityException {
        if (validators.get(e.getEntityCode()) != null) {
            Validator validator = validators.get(e.getEntityCode());
            validator.validate(e);
        }

        if (e instanceof Trackable)
            ((Trackable) e).setLastModificationDate(new Date());

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.copy()); // Update the entity in the database
                return;
            }
        }
        throw new EntityNotFoundException("Entity with ID " + e.id + " not found.");
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.get(entityCode) != null)
            throw new IllegalArgumentException(
                    "Validator for this entity code already exists. Please choose a different entity code");
        validators.put(entityCode, validator);
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> entityList = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getEntityCode() == entityCode)
                entityList.add(entity);
        }
        return entityList;
    }
    public static ArrayList<Step> getTasksSteps(int taskRef) {
        ArrayList<Step> stepList = new ArrayList<>();
        for (Entity entity: entities) {
            if (entity.getEntityCode() != Step.STEP_ENTITY_CODE)
                continue;
            if (((Step) entity).taskRef == taskRef)
                stepList.add((Step) entity);
        }
        if (stepList.isEmpty())
            return null;
        return stepList;
    }
}
