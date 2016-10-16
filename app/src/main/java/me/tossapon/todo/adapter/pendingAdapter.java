package me.tossapon.todo.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.tossapon.todo.R;
import me.tossapon.todo.model.Task;
import me.tossapon.todo.singletron.AdapterInstance;
import me.tossapon.todo.singletron.FabInstance;
import me.tossapon.todo.singletron.TaskInstace;

/**
 * Created by benvo_000 on 14/10/2559.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder>{
    private static final String TAG = "PENDING_ADP";
    ArrayList<Task> pendingTasks;
    ArrayList<Task> doneTasks;
    Context context;
    int[] imageId = {R.drawable.item1, R.drawable.item2, R.drawable.item3, R.drawable.item4};

    public PendingAdapter() {
        this.doneTasks = TaskInstace.getInstance().getDoneTask();
        this.pendingTasks = TaskInstace.getInstance().getPendingTask();
    }

    @Override
    public PendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_todoitem, parent, false);
        PendingViewHolder vh = new PendingViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PendingViewHolder holder, final int position) {
        Random rand = new Random();
        holder.cancel.setAlpha(0.0f);
        holder.itemname.setText(pendingTasks.get(position).getName());
        Picasso.with(context).load(imageId[rand.nextInt(4)]).into(holder.bg);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cancel.animate().alpha(1f).start();
                final Handler h = new Handler();
                final Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Task t = pendingTasks.get(holder.getAdapterPosition());
                        pendingTasks.remove(t);
                        t.setState(0);
                        doneTasks.add(t);
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), pendingTasks.size());
                        AdapterInstance.getInstance().getDoneAdapter().notifyDataSetChanged();

                        Log.d(TAG, "onAnimationEnd: " + pendingTasks.size());
                        Log.d(TAG, "onAnimationEnd: " + doneTasks.size());
                    }
                };

                h.postDelayed(r, 5000);
                holder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.cancel.animate().alpha(0.0f).start();
                        h.removeCallbacks(r);
                    }
                });
            }
        });
        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final FloatingActionButton fab = FabInstance.getInstance().getFab();
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
                fab.setImageResource(R.drawable.ic_delete_white_48dp);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getAdapterPosition();
                        Task task = pendingTasks.get(position);
                        pendingTasks.remove(task);
                        PendingAdapter.this.notifyItemRemoved(position);
                        PendingAdapter.this.notifyItemRangeChanged(position, doneTasks.size());
                        backToBasicFab(fab);
                    }
                });

                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backToBasicFab(fab);
                    }
                }, 5000);

                return true;
            }
        });
    }

    private void backToBasicFab(final FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_add_white_48dp);
        fab.setOnClickListener(FabInstance.getInstance().getFabDefaultRunnable());
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF4081")));
    }

    @Override
    public int getItemCount() {
        return pendingTasks.size();
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.root)
        RelativeLayout root;
        @BindView(R.id.cancel)
        Button cancel;
        @BindView(R.id.image)
        ImageView bg;
        @BindView(R.id.item_txtname)
        TextView itemname;
        public PendingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
