package me.tossapon.todo.components;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Component;
import me.tossapon.todo.activity.main.MainActivity;
import me.tossapon.todo.activity.main.fragment.DoneFragment;
import me.tossapon.todo.activity.main.fragment.PendingFragment;
import me.tossapon.todo.modules.NetworkModule;

/**
 * Created by benvo_000 on 13/10/2559.
 */
@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {
    void inject(MainActivity activity);
    void inject(PendingFragment pendingFragment);
    void inject(DoneFragment doneFragment);
}

