package me.tossapon.todo.singletron;

import java.util.ArrayList;

import me.tossapon.todo.model.Task;

/**
 * Created by benvo_000 on 14/10/2559.
 */
public class TaskInstace {
    private static TaskInstace ourInstance = new TaskInstace();
    private ArrayList<Task> doneTask;
    private ArrayList<Task> pendingTask;
    private int lastId;
    public static TaskInstace getInstance() {
        return ourInstance;
    }

    private TaskInstace() {
        doneTask = new ArrayList<>();
        pendingTask = new ArrayList<>();
    }

    public ArrayList<Task> getDoneTask() {
        return doneTask;
    }

    public ArrayList<Task> getPendingTask() {
        return pendingTask;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }
}
