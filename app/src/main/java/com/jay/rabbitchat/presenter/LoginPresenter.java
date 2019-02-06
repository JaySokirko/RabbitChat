package com.jay.rabbitchat.presenter;

import com.jay.rabbitchat.view.authorizationscreen.LoginContract;
import com.jay.rabbitchat.model.firebaseauthentification.SignIn;

import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.Model.SignInFeedBack {


    private LoginContract.View view;

    private LoginContract.Model model = new SignIn();

    @Inject
    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }


    @Override
    public void onSignInClick(String email, String password) {

        if (view != null){

            if (email.isEmpty()){
                view.emailEditTextError();

            } else if (password.isEmpty()){
                view.passwordEditTextError();

            } else {
                view.showProgress();
                model.signIn(this, email, password);
            }
        }
    }


    @Override
    public void onCreateNewAccountClick() {

        if (view != null){
            view.createNewAccount();
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }


    @Override
    public void onSuccessfulSignIn(boolean isSuccessful) {

        if (view!= null){
            view.hideProgress();
            view.startMainActivity();
        }
    }


    @Override
    public void onFailureSignIn(Throwable throwable) {

        if (view != null){
            view.hideProgress();
            view.signInError(throwable);
        }
    }
}
