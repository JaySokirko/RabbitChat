package com.jay.rabbitchat.view.authorizationscreen;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jay.rabbitchat.R;

import com.jay.rabbitchat.di.DaggerAppComponent;
import com.jay.rabbitchat.di.PresenterModule;
import com.jay.rabbitchat.di.SharedPreferencesModule;
import com.jay.rabbitchat.model.firebasedatabase.user.User;
import com.jay.rabbitchat.presenter.LoginPresenter;
import com.jay.rabbitchat.utils.SharedPrefManager;
import com.jay.rabbitchat.view.mainscreen.InterlocutorsContract;
import com.jay.rabbitchat.view.mainscreen.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * The user enters him login and password or creates a new account.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View {

    //activity's view
    private View destinationPoint;

    //activity's view
    private ImageView logoImView;

    @BindView(R.id.edit_text_login)
    EditText emailEditText;

    @BindView(R.id.edit_text_password)
    EditText passwordEditText;

    @BindView(R.id.registration_form_layout)
    LinearLayout registrationLayout;

    @BindView(R.id.button_sing_in)
    Button signIpBtn;

    @BindView(R.id.button_forgot_password)
    Button forgotPasswordBtn;

    @BindView(R.id.button_new_account)
    Button createAccountBtn;

    @BindView(R.id.text_view_email_error)
    TextView emailErrorTextView;

    @BindView(R.id.text_view_password_error)
    TextView passwordErrorTextView;

    @BindView(R.id.parent_view)
    RelativeLayout parentView;

    private Activity activity;

    @Inject
    LoginPresenter presenter;

    @Inject
    SharedPrefManager sharedPrefManager;


    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        DaggerAppComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(activity))
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        signIpBtn.setOnClickListener(this);
        forgotPasswordBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);

        startAnimationOnFragmentAttached();

        onEmailEditTextClickListener();
        onPasswordEditTextClickListener();

        return view;
    }



    private void startAnimationOnFragmentAttached() {

        new Handler().postDelayed(() -> {

            registrationLayout.animate().setDuration(1000).alpha(1).start();
            signIpBtn.animate().setDuration(1000).alpha(1).start();
            forgotPasswordBtn.animate().setDuration(1000).alpha(1).start();
            createAccountBtn.animate().setDuration(1000).alpha(1).start();
        }, 1500);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        logoImView = activity.findViewById(R.id.image_view_logo);

        //The point to which the logo will move.
        destinationPoint = activity.findViewById(R.id.view_top_point);

        new Handler().postDelayed(() -> {

            int[] pointCoordinates = new int[2];
            destinationPoint.getLocationOnScreen(pointCoordinates);
            float x = pointCoordinates[0];
            float y = pointCoordinates[1];

            int[] imageCoordinates = new int[2];
            logoImView.getLocationOnScreen(imageCoordinates);
            float x1 = imageCoordinates[0];
            float y1 = imageCoordinates[1];

            //if coordinates != 0 then move the logo image to left top corner
            if (x != 0 && y != 0 && x1 != x && y != y1) {
                logoImView.animate().setDuration(500).translationX(-(x1 - x))
                        .translationY(-(y1 - y)).start();
            }
        }, 1000);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_new_account:
                presenter.onCreateNewAccountClick();
                break;

            case R.id.button_sing_in:
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                presenter.onSignInClick(email, password);
                break;
        }
    }


    /**
     * If the user starts typing, the email error disappears.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void onEmailEditTextClickListener() {

        emailEditText.setOnTouchListener((v, event) -> {
            emailEditText.setBackground(getResources().getDrawable(R.drawable.rectangle_bottom_left_corner_90));
            emailErrorTextView.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    /**
     * If the user starts typing, the password error disappears.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void onPasswordEditTextClickListener() {

        passwordEditText.setOnTouchListener((v, event) -> {

            passwordEditText.setBackground(getResources().getDrawable(R.drawable.rectangle_bottom_left_corner_90));
            passwordErrorTextView.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    @Override
    public void startMainActivity() {
        // Sign in success
        sharedPrefManager.putBoolean("isUserSignedIn", true);

        startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }


    @Override
    public void createNewAccount() {

        FragmentManager manager = getFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            NewAccountFragment fragment = new NewAccountFragment();
            transaction.setCustomAnimations(R.anim.appear, R.anim.disappear);
            transaction.replace(R.id.authorization_container, fragment);
            transaction.commit();
        }
    }


    @Override
    public void emailEditTextError() {

        emailErrorTextView.setText(getResources().getString(R.string.enter_an_email));
        emailErrorTextView.setVisibility(View.VISIBLE);
        emailEditText.setBackground(getResources().getDrawable(R.drawable
                .rectangle_bottom_left_corner_90_error));
    }


    @Override
    public void passwordEditTextError() {

        passwordErrorTextView.setText(getResources().getString(R.string.enter_your_password));
        passwordErrorTextView.setVisibility(View.VISIBLE);
        passwordEditText.setBackground(getResources().getDrawable(R.drawable
                .rectangle_bottom_left_corner_90_error));
    }


    @Override
    public void signInError(Throwable throwable) {

        Snackbar.make(parentView, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
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
