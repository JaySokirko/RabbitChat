package com.jay.rabbitchat.presenter;

import com.jay.rabbitchat.view.mainscreen.SearchUsersContract;
import com.jay.rabbitchat.model.firebasedatabase.user.User;
import com.jay.rabbitchat.model.firebasedatabase.user.UsersListLoader;

import java.util.List;

import javax.inject.Inject;

public class SearchUsersPresenter implements SearchUsersContract.Presenter,
        SearchUsersContract.Model.LoadUsersFeedback {

    private SearchUsersContract.View view;

    private SearchUsersContract.Model model = new UsersListLoader();

    @Inject
    public SearchUsersPresenter(SearchUsersContract.View view) {
        this.view = view;
    }


    @Override
    public void loadUsersList() {

        if (view != null){

            view.showProgressBar();

            model.loadUsersList(this);
        }
    }


    @Override
    public void onDestroy() {

        view = null;
        model = null;
    }


    @Override
    public void onSuccessfulLoad(List<User> userList) {

        if (view != null){

            view.hideProgressBar();

            view.onLoadSuccessful(userList);
        }
    }


    @Override
    public void onFailureLoad(String errorMessage) {

        if (view != null){

            view.hideProgressBar();

            view.onLoadFailure(errorMessage);
        }
    }
}
