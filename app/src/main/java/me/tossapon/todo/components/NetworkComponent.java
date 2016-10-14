package me.tossapon.todo.components;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Component;
import me.tossapon.todo.activity.main.MainActivity;
import me.tossapon.todo.modules.NetworkModule;

/**
 * Created by benvo_000 on 13/10/2559.
 */
@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {
    void inject(MainActivity activity);
}

