package db.example;

import db.Entity;

public class Human extends Entity {
    public String name;
    public int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public Human copy() {
        Human copyHuman = new Human(name);
        copyHuman.id = id;
        copyHuman.age = age;

        return copyHuman;
    }
}