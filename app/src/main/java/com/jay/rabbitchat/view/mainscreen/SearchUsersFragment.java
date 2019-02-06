package com.jay.rabbitchat.view.mainscreen;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.jay.rabbitchat.R;

import com.jay.rabbitchat.di.DaggerAppComponent;
import com.jay.rabbitchat.di.PresenterModule;
import com.jay.rabbitchat.di.SharedPreferencesModule;
import com.jay.rabbitchat.model.adapter.UsersListAdapter;
import com.jay.rabbitchat.model.firebasedatabase.user.User;
import com.jay.rabbitchat.presenter.SearchUsersPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchUsersFragment extends Fragment implements SearchUsersContract.View {

    private Activity activity;

    //MainActivity's view
    private ImageView searchImageView;

    @BindView(R.id.search_users_edit_text)
    EditText searchUsersEditText;

    @BindView(R.id.users_list_recycler_view)
    RecyclerView usersRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private String TAG = "LOG_TAG";

    @Inject
    SearchUsersPresenter presenter;

    private UsersListAdapter adapter;

    private List<User> usersList = new ArrayList<>();

    public SearchUsersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        DaggerAppComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(activity))
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        usersRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        adapter = new UsersListAdapter(activity, usersList);
        usersRecyclerView.setAdapter(adapter);

        presenter.loadUsersList();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchImageView = activity.findViewById(R.id.search);
        searchImageView.setImageResource(R.drawable.ic_search);
        searchImageView.animate().setDuration(500).scaleX(1.2f).scaleY(1.2f).start();
    }


    @Override
    public void onPause() {
        super.onPause();

        searchImageView.animate().setDuration(500).scaleX(1.0f).scaleY(1.0f).start();
        searchImageView.setImageResource(R.drawable.ic_search_bw);
    }


    @Override
    public void showProgressBar() {

        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgressBar() {

        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onLoadSuccessful(List<User> userList) {

        usersList.clear();

        usersList.addAll(userList);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onLoadFailure(String errorMessage) {

        Snackbar.make(usersRecyclerView, errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
