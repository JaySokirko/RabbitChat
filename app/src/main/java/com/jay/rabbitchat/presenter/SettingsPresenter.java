package com.jay.rabbitchat.presenter;

import android.net.Uri;

import com.jay.rabbitchat.model.firebasedatabase.user.CurrentUserDataLoader;
import com.jay.rabbitchat.model.firebasestorage.UserImageUploader;
import com.jay.rabbitchat.view.mainscreen.SettingsContract;

import javax.inject.Inject;

public class SettingsPresenter implements SettingsContract.Presenter,
        SettingsContract.Model.LoadCurrentUserData.LoadCurrentUserDataFeedBack,
        SettingsContract.Model.UploadProfileImage.UploadProfileImageFeedback {

    public SettingsContract.View view;

    private SettingsContract.Model.LoadCurrentUserData currentUserDataLoader = new CurrentUserDataLoader();
    private SettingsContract.Model.UploadProfileImage uploaderProfileImage = new UserImageUploader();

    @Inject
    public SettingsPresenter(SettingsContract.View view) {
        this.view = view;
    }


    @Override
    public void loadCurrentUserData() {

        if (view != null) {

            view.showProgressBar();

            currentUserDataLoader.loadCurrentUserData(this);
        }
    }


    @Override
    public void uploadProfileImage(Uri profileImageUri, String fileExtension) {

        if (view != null) {

            uploaderProfileImage.uploadProfileImage(this, profileImageUri, fileExtension);
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        currentUserDataLoader = null;
    }


    @Override
    public void onLoadCurrentUserDataSuccessful(String profileImage, String userName) {

        if (view != null) {

            view.hideProgressBar();

            view.successfulLoadCurrentUserData(profileImage, userName);
        }
    }


    @Override
    public void onLoadCurrentUserDataFailure(String throwable) {

        if (view != null) {

            view.hideProgressBar();

            view.failureLoadCurrentUserData(throwable);
        }
    }


    @Override
    public void onSuccessfulUploadProfileImage() {

    }


    @Override
    public void onFailureUploadProfileImage(Throwable throwable) {

    }
}
