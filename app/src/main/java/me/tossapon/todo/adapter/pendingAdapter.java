package me.tossapon.todo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tossapon.todo.R;
import me.tossapon.todo.model.Task;

/**
 * Created by benvo_000 on 14/10/2559.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder>{

    ArrayList<Task> pendingTasks;
    Context context;

    public PendingAdapter(ArrayList<Task> pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    @Override
    public PendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_todoitem, parent, false);
        PendingViewHolder vh = new PendingViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PendingViewHolder holder, int position) {
        holder.name.setText(pendingTasks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pendingTasks.size();
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_name)
        TextView name;

        public PendingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
