package com.jay.rabbitchat.di;

import com.jay.rabbitchat.view.authorizationscreen.LoginFragment;
import com.jay.rabbitchat.view.authorizationscreen.NewAccountFragment;
import com.jay.rabbitchat.view.mainscreen.SearchUsersFragment;
import com.jay.rabbitchat.view.mainscreen.SettingsFragment;

import dagger.Component;

@Component(modules = {PresenterModule.class, SharedPreferencesModule.class})
public interface AppComponent {

    void inject(LoginFragment loginFragment);

    void inject(NewAccountFragment newAccountFragment);

    void inject(SettingsFragment settingsFragment);

    void inject(SearchUsersFragment searchUsersFragment);

}
