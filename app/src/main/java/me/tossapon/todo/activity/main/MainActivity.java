package me.tossapon.todo.activity.main;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tossapon.todo.R;
import me.tossapon.todo.TodoApp;
import me.tossapon.todo.activity.main.fragment.DoneFragment;
import me.tossapon.todo.activity.main.fragment.PendingFragment;
import me.tossapon.todo.api.TaskAPI;
import me.tossapon.todo.api.response.TaskResponse;
import me.tossapon.todo.singletron.TaskData;
import me.tossapon.todo.model.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fabAdd;
    @BindView(R.id.main_content)
    CoordinatorLayout mainLayout;

    @Inject
    Retrofit retrofit;

    ArrayList<Task> doneTask;
    ArrayList<Task> pendingTask;

    PendingFragment pendingFragment;
    DoneFragment doneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((TodoApp)getApplication()).getNetworkComponent().inject(this);
        doneTask = TaskData.getInstance().getDoneTask();
        pendingTask = TaskData.getInstance().getPendingTask();

        setSupportActionBar(toolbar);

        pendingFragment = PendingFragment.NewInstance();
        doneFragment = DoneFragment.NewInstance();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter
                .AddFragment(pendingFragment, "Pending")
                .AddFragment(doneFragment, "Done");

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TaskAPI api = retrofit.create(TaskAPI.class);
        Call<TaskResponse> response = api.getAllTask();
        response.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                ArrayList<Task> tasks = response.body().getData();
                for(int i = 0 ; i < tasks.size(); i++) {
                    if (tasks.get(i).getState() == 0)
                        pendingTask.add(tasks.get(i));
                    else
                        doneTask.add(tasks.get(i));
                }

                pendingFragment.NotifyDataChange();
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Snackbar.make(mainLayout, "Cannot retrieve data " + t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
