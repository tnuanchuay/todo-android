package me.tossapon.todo.activity.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.tossapon.todo.R;

/**
 * Created by benvo_000 on 12/10/2559.
 */

public class PendingFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending, container, false);
        return v;
    }

    public static PendingFragment NewInstance(){
        return new PendingFragment();
    }
}
