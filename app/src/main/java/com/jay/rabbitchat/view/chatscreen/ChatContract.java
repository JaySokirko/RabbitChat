package com.jay.rabbitchat.view.chatscreen;

import com.jay.rabbitchat.model.firebasedatabase.chat.Chat;

import java.util.Date;
import java.util.List;

public interface ChatContract {

    interface View{

        void showProgressBar();

        void hideProgressBar();

        void updateChat(List<Chat> messagesList);

        void sendMessage();
    }


    interface Presenter{

        void onUpdateChatListener(String userId);

        void onSendMessageClick(String message, String receiver);
    }


    interface Model{

        void updateChat(LoadChatFeedback listener, String userId);

        void sendMessage(String message, String receiver);

        interface LoadChatFeedback {

            void onLoadChatComplete(List<Chat> chatList);
        }
    }
}
