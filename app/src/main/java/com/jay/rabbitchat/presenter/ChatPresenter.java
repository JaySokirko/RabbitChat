package com.jay.rabbitchat.presenter;

import com.jay.rabbitchat.model.firebasedatabase.chat.Chat;
import com.jay.rabbitchat.model.firebasedatabase.chat.ChatWithUser;
import com.jay.rabbitchat.view.chatscreen.ChatContract;

import java.util.Date;
import java.util.List;

public class ChatPresenter implements ChatContract.Presenter, ChatContract.Model.LoadChatFeedback {

    private ChatContract.View view;

    private ChatContract.Model model= new ChatWithUser();


    public ChatPresenter(ChatContract.View view) {
        this.view = view;
    }

    @Override
    public void onUpdateChatListener(String userId) {

        if (view != null){

            view.showProgressBar();

            model.updateChat(this, userId);
        }
    }


    @Override
    public void onSendMessageClick(String message, String receiver) {

        if (view != null){

            if (message.isEmpty()){
                message = " ";
            }

            model.sendMessage(message, receiver);
        }
    }


    @Override
    public void onLoadChatComplete(List<Chat> chatList) {

        if (view != null){

            view.updateChat(chatList);

            view.hideProgressBar();
        }
    }
}
