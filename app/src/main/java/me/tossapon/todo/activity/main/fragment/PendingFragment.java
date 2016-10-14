package me.tossapon.todo.activity.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tossapon.todo.R;
import me.tossapon.todo.adapter.PendingAdapter;
import me.tossapon.todo.singletron.TaskData;
import me.tossapon.todo.model.Task;

/**
 * Created by benvo_000 on 12/10/2559.
 */

public class PendingFragment extends Fragment{

    @BindView(R.id.fpending_recyclerview)
    RecyclerView mRecyclerView;
    PendingAdapter adapter;

    ArrayList<Task> pendingTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending, container, false);
        ButterKnife.bind(this, v);
        pendingTask = TaskData.getInstance().getPendingTask();
        Log.d("PENDING_F", "onCreateView: " + pendingTask.size());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PendingAdapter(pendingTask);
        mRecyclerView.setAdapter(adapter);
        return v;
    }

    public static PendingFragment NewInstance(){
        return new PendingFragment();
    }

    public void NotifyDataChange(){
        if(adapter == null)
            return;
        adapter.notifyDataSetChanged();
    }
}
