package com.jay.rabbitchat.di;

import com.jay.rabbitchat.view.authorizationscreen.LoginContract;
import com.jay.rabbitchat.view.mainscreen.InterlocutorsContract;
import com.jay.rabbitchat.view.authorizationscreen.NewAccountContract;
import com.jay.rabbitchat.view.mainscreen.SearchUsersContract;
import com.jay.rabbitchat.view.mainscreen.SettingsContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {


    private InterlocutorsContract.View messagesView;

    public PresenterModule(InterlocutorsContract.View view) {
        this.messagesView = view;
    }

    @Provides
    InterlocutorsContract.View provideMessagesView(){
        return messagesView;
    }



    private SettingsContract.View settingsView;

    public PresenterModule(SettingsContract.View settingsView) {
        this.settingsView = settingsView;
    }

    @Provides
    SettingsContract.View provideSettingsView(){
        return settingsView;
    }



    private SearchUsersContract.View searchView;

    public PresenterModule(SearchUsersContract.View searchView) {
        this.searchView = searchView;
    }

    @Provides
    SearchUsersContract.View provideSearchView(){
        return searchView;
    }



    private LoginContract.View loginView;

    public PresenterModule(LoginContract.View loginView) {
        this.loginView = loginView;
    }

    @Provides
    LoginContract.View provideLoginView(){
        return loginView;
    }


    private NewAccountContract.View newAccountView;

    public PresenterModule(NewAccountContract.View newAccountView) {
        this.newAccountView = newAccountView;
    }

    @Provides
    public NewAccountContract.View provideNewAccountView() {
        return newAccountView;
    }
}
