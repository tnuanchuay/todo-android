package me.tossapon.todo.api;

import me.tossapon.todo.api.response.TaskResponse;
import me.tossapon.todo.model.Task;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by benvo_000 on 12/10/2559.
 */

public interface TaskAPI {
    @GET("u/6890301/tasks.json")
    Call<TaskResponse> getAllTask ();
}
