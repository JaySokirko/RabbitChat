package com.jay.rabbitchat.presenter;

import com.jay.rabbitchat.model.firebasedatabase.user.UserStatus;
import com.jay.rabbitchat.model.firebasedatabase.user.UserWasOnline;
import com.jay.rabbitchat.view.mainscreen.MainContract;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainContract.Model.UserStatusEditable statusEditor = new UserStatus();
    private MainContract.Model.UserWasOnlineEditable onlineEditor = new UserWasOnline();


    public MainPresenter(MainContract.View view) {
        this.view = view;
    }


    @Override
    public void setStatus(boolean status) {

        if (view != null){

            statusEditor.changeStatus(status);
        }
    }


    @Override
    public void setWasOnline() {

        if (view != null){

            onlineEditor.setWasOnline();
        }
    }


    @Override
    public void onDestroy() {

        statusEditor = null;
        view = null;
    }
}
