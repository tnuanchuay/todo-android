package me.tossapon.todo.activity.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tossapon.todo.R;
import me.tossapon.todo.TodoApp;
import me.tossapon.todo.activity.main.fragment.DoneFragment;
import me.tossapon.todo.activity.main.fragment.PendingFragment;
import me.tossapon.todo.adapter.DoneAdapter;
import me.tossapon.todo.adapter.PendingAdapter;
import me.tossapon.todo.api.TaskAPI;
import me.tossapon.todo.api.response.TaskResponse;
import me.tossapon.todo.singletron.AdapterInstance;
import me.tossapon.todo.singletron.FabInstance;
import me.tossapon.todo.singletron.TaskInstace;
import me.tossapon.todo.model.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static final String TAG="MAIN";

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
        doneTask = TaskInstace.getInstance().getDoneTask();
        pendingTask = TaskInstace.getInstance().getPendingTask();
        FabInstance.getInstance().setFab(fabAdd);

        setSupportActionBar(toolbar);

        pendingFragment = PendingFragment.NewInstance();
        doneFragment = DoneFragment.NewInstance();
        AdapterInstance.getInstance().setDoneAdapter(new DoneAdapter());
        AdapterInstance.getInstance().setPendingAdapter(new PendingAdapter());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter
                .AddFragment(pendingFragment, "Pending")
                .AddFragment(doneFragment, "Done");

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0)
                    fabAdd.show();
                else
                    fabAdd.hide();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FabInstance.getInstance().setFabDefaultRunnable(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText taskName;
                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_new_task, null);
                taskName = (EditText) v.findViewById(R.id.task_name);
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setView(v)
                        .setTitle("todo")
                        .setMessage("Add new Tasks")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int lastId = TaskInstace.getInstance().getLastId()+1;
                                pendingTask.add(new Task(lastId, taskName.getText().toString(), 0));
                                AdapterInstance.getInstance().getPendingAdapter().notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        fabAdd.setOnClickListener(FabInstance.getInstance().getFabDefaultRunnable());

        getTasksData();
    }

    private void getTasksData() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Loading Task data", true);
        TaskAPI api = retrofit.create(TaskAPI.class);
        Call<TaskResponse> response = api.getAllTask();
        response.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                ArrayList<Task> tasks = response.body().getData();
                pendingTask.clear();
                doneTask.clear();
                for(int i = 0 ; i < tasks.size(); i++) {
                    int lastId = TaskInstace.getInstance().getLastId();
                    Task task = tasks.get(i);
                    if (task.getState() == 0)
                        pendingTask.add(tasks.get(i));
                    else
                        doneTask.add(tasks.get(i));

                    if(lastId < task.getId())
                        TaskInstace.getInstance().setLastId(task.getId());
                }

                AdapterInstance.getInstance().getDoneAdapter().notifyDataSetChanged();
                AdapterInstance.getInstance().getPendingAdapter().notifyDataSetChanged();
                Log.d(TAG, "onResponse: " + doneTask.size());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Snackbar.make(mainLayout, "Cannot retrieve data " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}
