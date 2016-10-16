package me.tossapon.todo.singletron;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by benvo_000 on 16/10/2559.
 */
public class FabInstance {
    private static FabInstance ourInstance = new FabInstance();

    private FloatingActionButton fab;
    private View.OnClickListener fabDefaultRunnable;

    public static FabInstance getInstance() {
        return ourInstance;
    }

    private FabInstance() {
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    public View.OnClickListener getFabDefaultRunnable() {
        return fabDefaultRunnable;
    }

    public void setFabDefaultRunnable(View.OnClickListener fabDefaultRunnable) {
        this.fabDefaultRunnable = fabDefaultRunnable;
    }
}
