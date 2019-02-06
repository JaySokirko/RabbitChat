package com.jay.rabbitchat.di;

import com.jay.rabbitchat.view.LauncherActivity;
import com.jay.rabbitchat.view.mainscreen.GroupsFragment;

import dagger.Component;

@Component(modules = SharedPreferencesModule.class)
public interface SharedPrefComponent {

   void inject(LauncherActivity activity);

   void inject(GroupsFragment groupsFragment);
}
