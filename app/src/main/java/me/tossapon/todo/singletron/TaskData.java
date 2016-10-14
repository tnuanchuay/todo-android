package me.tossapon.todo.singletron;

import java.util.ArrayList;

import me.tossapon.todo.model.Task;

/**
 * Created by benvo_000 on 14/10/2559.
 */
public class TaskData {
    private static TaskData ourInstance = new TaskData();
    private ArrayList<Task> doneTask;
    private ArrayList<Task> pendingTask;
    public static TaskData getInstance() {
        return ourInstance;
    }

    private TaskData() {
        doneTask = new ArrayList<>();
        pendingTask = new ArrayList<>();
    }

    public ArrayList<Task> getDoneTask() {
        return doneTask;
    }

    public ArrayList<Task> getPendingTask() {
        return pendingTask;
    }
}
