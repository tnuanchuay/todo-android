package me.tossapon.todo.model;

/**
 * Created by benvo_000 on 12/10/2559.
 */

public class Task {
    private int id;
    private String name;
    private int state;

    public Task(int id, String name, int state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(int state) {
        this.state = state;
    }
}
