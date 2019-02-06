package com.jay.rabbitchat.view.authorizationscreen;

public interface NewAccountContract {

    interface View{

        void startMainActivity();

        void cancelCreateAccount();

        void createAccountError(Throwable throwable);

        void emailEditTextError();

        void userNameEditTextError();

        void passwordEditTextError();

        void showProgress();

        void hideProgress();
    }


    interface Presenter{

        void onCreateAccountClick(String userName, String email, String password);

        void onCancelCreateAccountClick();

        void onDestroy();
    }


    interface Model{

        interface CreateAccountFeedback {

            void onSuccessCreateAccount(boolean isSuccess);

            void onFailureCreateAccount(Throwable throwable);
        }

        void createAccount(CreateAccountFeedback feedback, String userName, String email, String password);
    }
}
