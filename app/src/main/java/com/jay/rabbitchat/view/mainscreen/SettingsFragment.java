package com.jay.rabbitchat.view.mainscreen;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.jay.rabbitchat.R;
import com.jay.rabbitchat.di.DaggerAppComponent;
import com.jay.rabbitchat.di.PresenterModule;
import com.jay.rabbitchat.di.SharedPreferencesModule;
import com.jay.rabbitchat.presenter.SettingsPresenter;
import com.jay.rabbitchat.utils.SharedPrefManager;
import com.jay.rabbitchat.view.LauncherActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements SettingsContract.View {

    private Activity activity;

    //MainActivity's view
    private ImageView settingsImageView;

    @BindView(R.id.current_user_image)
    CircleImageView profileImageView;

    @BindView(R.id.current_user_name)
    TextView userNameTextView;

    @BindView(R.id.parent_layout)
    FrameLayout parentLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String TAG = "LOG_TAG";

    @Inject
    SharedPrefManager sharedPrefManager;

    @Inject
    SettingsPresenter presenter;

    private final int GET_PROFILE_IMAGE_REQUEST = 111;


    public SettingsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, view);

        DaggerAppComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(activity))
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);

        presenter.loadCurrentUserData();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settingsImageView = activity.findViewById(R.id.settings);
        settingsImageView.setImageResource(R.drawable.ic_settings);
        settingsImageView.animate().setDuration(500).scaleX(1.2f).scaleY(1.2f).start();
    }


    @Override
    public void onPause() {
        super.onPause();

        settingsImageView.animate().setDuration(500).scaleX(1.0f).scaleY(1.0f).start();
        settingsImageView.setImageResource(R.drawable.ic_settings_bw);
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
    @OnClick(R.id.logout_button)
    public void logout() {

        sharedPrefManager.putBoolean("isUserSignedIn", false);

        activity.startActivity(new Intent(activity, LauncherActivity.class));
        activity.finish();
    }


    @Override
    @OnClick(R.id.current_user_image)
    public void changeProfileImage() {

        Intent openGalleryIntent = new Intent();
        openGalleryIntent.setType("image/*");
        openGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(openGalleryIntent, GET_PROFILE_IMAGE_REQUEST);
    }


    @Override
    public String getFileExtension(Uri uri) {

        ContentResolver contentResolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_PROFILE_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Uri profileImageIri = data.getData();

            profileImageView.setImageURI(profileImageIri);

            presenter.uploadProfileImage(profileImageIri, getFileExtension(profileImageIri));
        }
    }


    @Override
    public void successfulLoadCurrentUserData(String profileImage, String userName) {

        userNameTextView.setText(userName);

        if (profileImage.equals("default")) {

            profileImageView.setImageResource(R.drawable.ic_user_without_photo);
        } else {
            Glide.with(activity).load(profileImage).into(profileImageView);
        }
    }


    @Override
    public void failureLoadCurrentUserData(String throwable) {

        Snackbar.make(parentLayout, throwable, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.onDestroy();
    }
}
