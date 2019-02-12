package com.jay.rabbitchat.view.mainscreen;

public interface MainContract {


    interface View{

    }


    interface Presenter{

        void setStatus(boolean status);

        void setWasOnline();

        void onDestroy();
    }


    interface Model{

        //Set user's status online or offline
        interface UserStatusEditable {

            void changeStatus(boolean status);
        }

        //When the user was online.
        interface UserWasOnlineEditable {

            void setWasOnline();
        }
    }
}
