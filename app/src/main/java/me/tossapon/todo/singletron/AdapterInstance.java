package me.tossapon.todo.singletron;

import me.tossapon.todo.adapter.DoneAdapter;
import me.tossapon.todo.adapter.PendingAdapter;

/**
 * Created by benvo_000 on 15/10/2559.
 */
public class AdapterInstance {
    private static AdapterInstance ourInstance = new AdapterInstance();
    private PendingAdapter pendingAdapter;
    private DoneAdapter doneAdapter;
    public static AdapterInstance getInstance() {
        return ourInstance;
    }

    private AdapterInstance() {
    }

    public PendingAdapter getPendingAdapter() {
        return pendingAdapter;
    }

    public DoneAdapter getDoneAdapter() {
        return doneAdapter;
    }

    public void setPendingAdapter(PendingAdapter pendingAdapter) {
        this.pendingAdapter = pendingAdapter;
    }

    public void setDoneAdapter(DoneAdapter doneAdapter) {
        this.doneAdapter = doneAdapter;
    }
}
