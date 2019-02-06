package com.jay.rabbitchat.view.mainscreen;

import android.net.Uri;

public interface SettingsContract {

    interface View {

        void showProgressBar();

        void hideProgressBar();

        void logout();

        void changeProfileImage();

        void successfulLoadCurrentUserData(String profileImage, String userName);

        void failureLoadCurrentUserData(String throwable);

        String getFileExtension(Uri uri);
    }


    interface Presenter {

        void loadCurrentUserData();

        void uploadProfileImage(Uri profileImageUri, String fileExtension);

        void onDestroy();
    }


    interface Model {

        interface LoadCurrentUserData {

            void loadCurrentUserData(LoadCurrentUserDataFeedBack feedBack);

            interface LoadCurrentUserDataFeedBack {

                void onLoadCurrentUserDataSuccessful(String profileImage, String userName);

                void onLoadCurrentUserDataFailure(String throwable);
            }
        }


        interface UploadProfileImage {

            void uploadProfileImage(UploadProfileImageFeedback feedback, Uri profileImageUri,
                                    String fileExtension);

            interface UploadProfileImageFeedback {

                void onSuccessfulUploadProfileImage();

                void onFailureUploadProfileImage(Throwable throwable);
            }
        }
    }
}
