package com.jay.rabbitchat.view.mainscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jay.rabbitchat.R;
import com.jay.rabbitchat.presenter.MainPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainContract.View {

    private InterlocutorsFragment interlocutorsFragment;
    private GroupsFragment groupsFragment;
    private SearchUsersFragment searchFragment;
    private SettingsFragment settingsFragment;

    private FragmentManager manager;

    private MainPresenter presenter;


    @BindView(R.id.messages) ImageView messagesImageView;

    @BindView(R.id.groups) ImageView groupsImageView;

    @BindView(R.id.search) ImageView searchImageView;

    @BindView(R.id.settings) ImageView settingsImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        messagesImageView.setOnClickListener(this);
        groupsImageView.setOnClickListener(this);
        searchImageView.setOnClickListener(this);
        settingsImageView.setOnClickListener(this);

        presenter = new MainPresenter(this);

        setupFragments();
    }


    private void setupFragments() {

        manager = getSupportFragmentManager();
        interlocutorsFragment = new InterlocutorsFragment();
        groupsFragment = new GroupsFragment();
        searchFragment = new SearchUsersFragment();
        settingsFragment = new SettingsFragment();
    }


    @Override
    public void onClick(View v) {

        FragmentTransaction transaction = manager.beginTransaction();

        switch (v.getId()) {

            case R.id.messages:
                transaction.replace(R.id.main_container, interlocutorsFragment);
                break;

            case R.id.groups:
                transaction.replace(R.id.main_container, groupsFragment);
                break;

            case R.id.search:
                transaction.replace(R.id.main_container, searchFragment);
                break;

            case R.id.settings:
                transaction.replace(R.id.main_container, settingsFragment);
                break;
        }
        transaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();

        //the user online
        presenter.setStatus(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //the user offline
        presenter.setStatus(false);
        presenter.setWasOnline();
        presenter.onDestroy();
    }
}
