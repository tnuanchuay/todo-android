package me.tossapon.todo;

import android.app.Application;

import me.tossapon.todo.components.DaggerNetworkComponent;
import me.tossapon.todo.components.NetworkComponent;

/**
 * Created by benvo_000 on 13/10/2559.
 */

public class TodoApp extends Application {
    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        networkComponent = DaggerNetworkComponent.create();
        super.onCreate();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
