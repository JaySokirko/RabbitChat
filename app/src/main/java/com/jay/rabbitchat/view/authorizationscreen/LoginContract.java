package com.jay.rabbitchat.view.authorizationscreen;

public interface LoginContract {

    interface View{

        void startMainActivity();

        void createNewAccount();

        void emailEditTextError();

        void passwordEditTextError();

        void signInError(Throwable throwable);

        void showProgress();

        void hideProgress();
    }


    interface Presenter{

        void onSignInClick(String email, String password);

        void onCreateNewAccountClick();

        void onDestroy();
    }


    interface Model{

        interface SignInFeedBack{

            void onSuccessfulSignIn(boolean isSuccessful);

            void onFailureSignIn(Throwable throwable);
        }

        void signIn(SignInFeedBack feedBack, String email, String password);
    }
}
