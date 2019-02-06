package com.jay.rabbitchat.view.authorizationscreen;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jay.rabbitchat.R;
import com.jay.rabbitchat.di.DaggerAppComponent;
import com.jay.rabbitchat.di.PresenterModule;
import com.jay.rabbitchat.di.SharedPreferencesModule;
import com.jay.rabbitchat.utils.SharedPrefManager;
import com.jay.rabbitchat.presenter.NewAccountPresenter;
import com.jay.rabbitchat.view.mainscreen.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewAccountFragment extends Fragment implements View.OnClickListener,
        NewAccountContract.View {

    private LoginFragment fragment;

    //AuthorizationActivity's view
    private ImageView logoImView;

    //todo onImageClick
    @BindView(R.id.add_avatar_image)
    CircleImageView profileImage;

    @BindView(R.id.edit_email)
    EditText emailEditText;

    @BindView(R.id.edit_nick_name)
    EditText nameEditText;

    @BindView(R.id.edit_password)
    EditText passwordEditText;

    @BindView(R.id.accept_button)
    Button acceptBtn;

    @BindView(R.id.cancel_button)
    Button cancelBtn;

    @BindView(R.id.parent_layout)
    RelativeLayout parentLayout;

    @BindView(R.id.text_view_email_error)
    TextView emailErrorTextView;

    @BindView(R.id.text_view_password_error)
    TextView passwordErrorTextView;

    @BindView(R.id.text_view_nick_name_error)
    TextView nickNameErrorTextView;

    @Inject
    public NewAccountPresenter presenter;

    @Inject
    public SharedPrefManager sharedPrefManager;

    public Activity activity;


    public NewAccountFragment() {
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
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        ButterKnife.bind(this, view);

        DaggerAppComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(activity))
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        acceptBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        onEmailEditListener();
        onNickNameEditListener();
        onPasswordEditListener();

        presenter = new NewAccountPresenter(this);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        logoImView = activity.findViewById(R.id.image_view_logo);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cancel_button:
                presenter.onCancelCreateAccountClick();
                break;

            case R.id.accept_button:

                String email = emailEditText.getText().toString();
                String nickName = nameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                presenter.onCreateAccountClick(nickName, email, password);
                break;
        }
    }


    /**
     * Hide the error when the user starts typing the email address
     */
    @SuppressLint("ClickableViewAccessibility")
    private void onEmailEditListener() {

        emailEditText.setOnTouchListener((v, event) -> {
            emailEditText.setBackground(getResources().getDrawable(R.drawable
                    .rectangle_bottom_left_corner_90));
            emailErrorTextView.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    /**
     * Hide the error when the user starts typing the nickname
     */
    @SuppressLint("ClickableViewAccessibility")
    private void onNickNameEditListener() {

        nickNameErrorTextView.setOnTouchListener((v, event) -> {
            nameEditText.setBackground(getResources().getDrawable(R.drawable
                    .rectangle_bottom_left_corner_90));
            nickNameErrorTextView.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    /**
     * Hide the error when the user starts typing the password
     */
    @SuppressLint("ClickableViewAccessibility")
    private void onPasswordEditListener() {

        passwordEditText.setOnTouchListener((v, event) -> {
            passwordEditText.setBackground(getResources().getDrawable(R.drawable
                    .rectangle_bottom_left_corner_90));
            passwordErrorTextView.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    @Override
    public void startMainActivity() {

        //successful created new account
        sharedPrefManager.putBoolean("isUserSignedIn", true);

        startActivity(new Intent(getActivity(), MainActivity.class));
        activity.finish();
    }


    @Override
    public void cancelCreateAccount() {

        FragmentManager manager = getFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            if (fragment == null) {
                fragment = new LoginFragment();
                transaction.setCustomAnimations(R.anim.appear, R.anim.disappear);
                transaction.replace(R.id.authorization_container, fragment);
                transaction.commit();
            }
        }
    }


    @Override
    public void createAccountError(Throwable throwable) {

        Snackbar.make(parentLayout, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void emailEditTextError() {

        emailErrorTextView.setText(getResources().getString(R.string.enter_an_email));
        emailErrorTextView.setVisibility(View.VISIBLE);
        emailEditText.setBackground(getResources().getDrawable(R.drawable.rectangle_bottom_left_corner_90_error));
    }


    @Override
    public void userNameEditTextError() {

        nickNameErrorTextView.setText(getString(R.string.enter_your_nick_name));
        nickNameErrorTextView.setVisibility(View.VISIBLE);
        nameEditText.setBackground(getResources().getDrawable(R.drawable.rectangle_bottom_left_corner_90_error));
    }


    @Override
    public void passwordEditTextError() {

        passwordErrorTextView.setText(getResources().getString(R.string.enter_your_password));
        passwordErrorTextView.setVisibility(View.VISIBLE);
        passwordEditText.setBackground(getResources().getDrawable(R.drawable.rectangle_bottom_left_corner_90_error));
    }

    @Override
    public void showProgress() {
        logoImView.setImageResource(R.drawable.logo_animated_progress);
        Drawable drawable = logoImView.getDrawable();
        logoImView.post(((Animatable) drawable)::start);
    }

    @Override
    public void hideProgress() {
        logoImView.setImageResource(R.drawable.logo_rabbit);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.onDestroy();
    }
}
