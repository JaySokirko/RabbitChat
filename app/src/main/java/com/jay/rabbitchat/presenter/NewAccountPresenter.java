package com.jay.rabbitchat.presenter;

import com.jay.rabbitchat.view.authorizationscreen.NewAccountContract;
import com.jay.rabbitchat.model.firebaseauthentification.NewAccount;

import javax.inject.Inject;

public class NewAccountPresenter implements NewAccountContract.Presenter,
        NewAccountContract.Model.CreateAccountFeedback {

    private NewAccountContract.View view;
    private NewAccountContract.Model model = new NewAccount();

    @Inject
    public NewAccountPresenter(NewAccountContract.View view) {
        this.view = view;
    }


    @Override
    public void onSuccessCreateAccount(boolean isSuccess) {

        if (view != null) {

            if (isSuccess) {
                view.hideProgress();
                view.startMainActivity();
            }
        }
    }


    @Override
    public void onFailureCreateAccount(Throwable throwable) {

        if (view != null) {
            view.hideProgress();
            view.createAccountError(throwable);
        }
    }


    @Override
    public void onCreateAccountClick(String userName, String email, String password) {

        if (view != null) {

            if (email.isEmpty()) {
                view.emailEditTextError();

            } else if (userName.isEmpty()) {
                view.userNameEditTextError();

            } else if (password.isEmpty()) {
                view.passwordEditTextError();

            } else {
                view.showProgress();
                model.createAccount(this, userName, email, password);
            }
        }
    }


    @Override
    public void onCancelCreateAccountClick() {

        if (view != null) {
            view.cancelCreateAccount();
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }
}
