package com.jay.rabbitchat.view.mainscreen;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jay.rabbitchat.R;
import com.jay.rabbitchat.model.adapter.ChatListAdapter;
import com.jay.rabbitchat.model.firebasedatabase.chat.Interlocutors;
import com.jay.rabbitchat.model.firebasedatabase.user.User;
import com.jay.rabbitchat.presenter.InterlocutorsPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterlocutorsFragment extends Fragment implements InterlocutorsContract.View {

    private Activity activity;

    //MainActivity's view
    private ImageView messagesImageView;

    @BindView(R.id.recycler_view_messages)
    RecyclerView interlocutorsRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    //    @Inject
    private InterlocutorsPresenter presenter;

    private ChatListAdapter chatListAdapter;

    private List<User> userList = new ArrayList<>();


    public InterlocutorsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        ButterKnife.bind(this, view);

        setupInterlocutorsRecyclerView();

        presenter = new InterlocutorsPresenter(this);
        presenter.showInterlocutors();

        return view;
    }


    private void setupInterlocutorsRecyclerView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        interlocutorsRecyclerView.setLayoutManager(linearLayoutManager);

        chatListAdapter = new ChatListAdapter(activity, userList);
        interlocutorsRecyclerView.setAdapter(chatListAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        messagesImageView = activity.findViewById(R.id.messages);
        messagesImageView.setImageResource(R.drawable.ic_messages);
        messagesImageView.animate().setDuration(500).scaleX(1.2f).scaleY(1.2f).start();
    }


    @Override
    public void onPause() {
        super.onPause();

        messagesImageView.animate().setDuration(500).scaleX(1.0f).scaleY(1.0f).start();
        messagesImageView.setImageResource(R.drawable.ic_messages_bw);
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
    public void loadInterlocutorsSuccessful(List<User> users) {

        userList.clear();
        userList.addAll(users);

        chatListAdapter.notifyDataSetChanged();

        Log.d(TAG, "loadInterlocutorsSuccessful: " + users);
    }


    @Override
    public void loadInterlocutorsFailure(Throwable throwable) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
