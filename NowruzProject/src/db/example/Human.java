package db.example;

import db.Entity;

public class Human extends Entity {
    public String name;
    public int age;

    public Human(String name) {
        this.name = name;
    }
    @Override
    public Human copy() {
        Human copyHuman = new Human(name);
        copyHuman.id = id;

        return copyHuman;
    }
}