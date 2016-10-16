package me.tossapon.todo.singletron;

import android.support.v7.widget.RecyclerView;

import me.tossapon.todo.adapter.DoneAdapter;
import me.tossapon.todo.adapter.PendingAdapter;

/**
 * Created by benvo_000 on 15/10/2559.
 */
public class AdapterSingletron {
    private static AdapterSingletron ourInstance = new AdapterSingletron();
    private PendingAdapter pendingAdapter;
    private DoneAdapter doneAdapter;
    public static AdapterSingletron getInstance() {
        return ourInstance;
    }

    private AdapterSingletron() {
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
