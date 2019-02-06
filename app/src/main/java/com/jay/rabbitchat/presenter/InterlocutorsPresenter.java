package com.jay.rabbitchat.presenter;

import com.jay.rabbitchat.model.firebasedatabase.chat.InterlocutorsListLoader;
import com.jay.rabbitchat.model.firebasedatabase.user.User;
import com.jay.rabbitchat.view.mainscreen.InterlocutorsContract;

import java.util.List;

import javax.inject.Inject;

public class InterlocutorsPresenter implements InterlocutorsContract.Presenter,
        InterlocutorsContract.Model.LoadInterlocutorsFeedback {

    private InterlocutorsContract.View view;

    private InterlocutorsContract.Model model = new InterlocutorsListLoader();

    @Inject
    public InterlocutorsPresenter(InterlocutorsContract.View view) {
        this.view = view;
    }


    @Override
    public void showInterlocutors() {

        if (view != null){

            view.showProgressBar();

            model.loadInterlocutors(this);
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }


    @Override
    public void onLoadInterlocutorsSuccess(List<User> users) {

        if (view != null){

            view.loadInterlocutorsSuccessful(users);
            view.hideProgressBar();
        }
    }


    @Override
    public void onLoadInterlocutorsFailure(Throwable throwable) {

        if (view != null){

            view.loadInterlocutorsFailure(throwable);
            view.hideProgressBar();
        }
    }
}
