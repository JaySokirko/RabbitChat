package com.jay.rabbitchat.view.mainscreen;

import com.jay.rabbitchat.model.firebasedatabase.user.User;

import java.util.List;

public interface InterlocutorsContract {

    interface View {

        void showProgressBar();

        void hideProgressBar();

        void loadInterlocutorsSuccessful(List<User> users);

        void loadInterlocutorsFailure(Throwable throwable);
    }


    interface Presenter {

        void showInterlocutors();

        void onDestroy();
    }


    interface Model {

        interface LoadInterlocutorsFeedback {

            void onLoadInterlocutorsSuccess(List<User> users);

            void onLoadInterlocutorsFailure(Throwable throwable);
        }

        void loadInterlocutors(LoadInterlocutorsFeedback feedback);
    }
}
