package com.jay.rabbitchat.view.chatscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jay.rabbitchat.R;
import com.jay.rabbitchat.model.adapter.ChatWithUserAdapter;
import com.jay.rabbitchat.model.firebasedatabase.chat.Chat;
import com.jay.rabbitchat.presenter.ChatPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatContract.View {

    @BindView(R.id.interlocutor_name)
    TextView interlocutorNameTextView;

    @BindView(R.id.interlocutor_profile_image)
    CircleImageView interlocutorImage;

    @BindView(R.id.chat_toolbar)
    Toolbar toolbar;

    @BindView(R.id.edit_message)
    EditText typeMessageEditText;

    @BindView(R.id.send_message)
    ImageButton sendMessageBtn;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.messages_recycler_view)
    RecyclerView messagesRecyclerView;

    private ChatWithUserAdapter chatAdapter;

    private List<Chat> messagesList = new ArrayList<>();

    ChatPresenter presenter;

    private String TAG = "LOG";
    private String receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        setupToolbar();

        setupMessagesRecyclerView();

        //User ID to send messages.
        receiver = getIntent().getStringExtra("userId");

        presenter = new ChatPresenter(this);

        presenter.onUpdateChatListener(receiver);
    }


    private void setupMessagesRecyclerView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        messagesRecyclerView.setLayoutManager(linearLayoutManager);

        chatAdapter = new ChatWithUserAdapter(this, messagesList);
        messagesRecyclerView.setAdapter(chatAdapter);
    }


    private void setupToolbar(){

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> finish());
        }

        String interlocutorName = getIntent().getStringExtra("userName");
        String interlocutorProfileImageUrl = getIntent().getStringExtra("userImageUrl");

        if (interlocutorProfileImageUrl.equals("default")){

            interlocutorImage.setImageResource(R.drawable.ic_user_without_photo);
        } else {
            Glide.with(this).load(interlocutorProfileImageUrl).into(interlocutorImage);
        }

        interlocutorNameTextView.setText(interlocutorName);
    }


    @Override
    @OnClick(R.id.send_message)
    public void sendMessage(){

        String message = typeMessageEditText.getText().toString();

        typeMessageEditText.setText("");

        presenter.onSendMessageClick(message, receiver);
    }


    @Override
    public void showProgressBar() {

        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgressBar() {

        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void updateChat(List<Chat> messages) {

        messagesList.clear();
        messagesList.addAll(messages);

        chatAdapter.notifyDataSetChanged();
        //scroll to the bottom
        messagesRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
    }
}
