package com.jay.rabbitchat.view.mainscreen;

import com.jay.rabbitchat.model.firebasedatabase.user.User;

import java.util.List;

public interface SearchUsersContract {

    interface View{

        void showProgressBar();

        void hideProgressBar();

        void onLoadSuccessful(List<User> usersList);

        void onLoadFailure(String errorMessage);
    }


    interface  Presenter{

        void loadUsersList();

        void onDestroy();
    }


    interface Model{

        interface LoadUsersFeedback{

            void onSuccessfulLoad(List<User> usersList);

            void onFailureLoad(String errorMessage);
        }

        void loadUsersList(LoadUsersFeedback feedback);
    }

}
