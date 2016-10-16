package me.tossapon.todo.activity.main.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tossapon.todo.R;
import me.tossapon.todo.TodoApp;
import me.tossapon.todo.adapter.DoneAdapter;
import me.tossapon.todo.adapter.PendingAdapter;
import me.tossapon.todo.api.TaskAPI;
import me.tossapon.todo.api.response.TaskResponse;
import me.tossapon.todo.model.Task;
import me.tossapon.todo.singletron.AdapterSingletron;
import me.tossapon.todo.singletron.TaskData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by benvo_000 on 12/10/2559.
 */

public class DoneFragment extends Fragment {
    private static final String TAG = "DONE_FRAGMENT";
    @BindView(R.id.fdone_srlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fdone_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.root)
    RelativeLayout root;

    DoneAdapter adapter;

    ArrayList<Task> pendingTask;
    ArrayList<Task> doneTask;

    @Inject
    Retrofit retrofit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_done, container, false);
        ButterKnife.bind(this, v);
        pendingTask = TaskData.getInstance().getPendingTask();
        doneTask = TaskData.getInstance().getDoneTask();
        ((TodoApp)getActivity().getApplication()).getNetworkComponent().inject(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setHasFixedSize(false);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = AdapterSingletron.getInstance().getDoneAdapter();
        mRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTasksData();
            }
        });
        return v;
    }

    public static DoneFragment NewInstance(){
        return new DoneFragment();
    }

    private void getTasksData() {
        TaskAPI api = retrofit.create(TaskAPI.class);
        Call<TaskResponse> response = api.getAllTask();
        response.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                ArrayList<Task> tasks = response.body().getData();
                pendingTask.clear();
                doneTask.clear();
                for(int i = 0 ; i < tasks.size(); i++) {
                    if (tasks.get(i).getState() == 0)
                        pendingTask.add(tasks.get(i));
                    else
                        doneTask.add(tasks.get(i));
                }
                AdapterSingletron.getInstance().getDoneAdapter().notifyDataSetChanged();
                AdapterSingletron.getInstance().getPendingAdapter().notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Snackbar.make(root, "Cannot retrieve data " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
