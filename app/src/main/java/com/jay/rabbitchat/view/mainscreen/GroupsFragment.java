package com.jay.rabbitchat.view.mainscreen;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jay.rabbitchat.R;
import com.jay.rabbitchat.di.DaggerSharedPrefComponent;
import com.jay.rabbitchat.di.SharedPreferencesModule;
import com.jay.rabbitchat.utils.SharedPrefManager;

import javax.inject.Inject;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {

    private ImageView groupsImageView;
    private Activity activity;

    @Inject
    SharedPrefManager prefManager;


    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = getActivity();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        DaggerSharedPrefComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(activity))
                .build()
                .inject(this);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        groupsImageView = activity.findViewById(R.id.groups);
        groupsImageView.setImageResource(R.drawable.ic_group);
        groupsImageView.animate().setDuration(500).scaleX(1.2f).scaleY(1.2f).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        groupsImageView.animate().setDuration(500).scaleX(1.0f).scaleY(1.0f).start();
        groupsImageView.setImageResource(R.drawable.ic_group_bw);
    }
}
