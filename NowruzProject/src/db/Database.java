package db;

import java.util.ArrayList;
import db.exception.EntityNotFoundException;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    static int nextId = 1; // Next ID to be assigned to a new entity

    private Database() {}   // Private constructor to prevent instantiation

    public static void add(Entity e) {
        e.id = nextId++;
        entities.add(e);
    }
    public static Entity get(int id) {
        for (Entity e : entities) {
            if (e.id == id) {
                return e;
            }
        }
        throw new EntityNotFoundException(id);
    }
    public static void delete(int id) {
        for (Entity entity: entities) {
            if (entity.id == id) {
                entities.remove(id);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }
    public static void update(Entity e) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e); // Update the entity in the database
                return;
            }
        }
        throw new EntityNotFoundException("Entity with ID " + e.id + " not found.");
    }
}
