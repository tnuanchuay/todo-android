package me.tossapon.todo.api.response;

import java.util.ArrayList;

import me.tossapon.todo.model.Task;

/**
 * Created by benvo_000 on 12/10/2559.
 */

public class TaskResponse {
    ArrayList<Task> data;

    public TaskResponse(ArrayList<Task> data) {
        this.data = data;
    }

    public ArrayList<Task> getData() {
        return data;
    }
}
