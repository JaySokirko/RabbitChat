package com.jay.rabbitchat.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jay.rabbitchat.R;
import com.jay.rabbitchat.model.firebasedatabase.chat.Chat;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWithUserAdapter extends RecyclerView.Adapter<ChatWithUserAdapter.ViewHolder> {

    private final int MESSAGE_TYPE_SENT = 0;
    private final int MESSAGE_TYPE_INCOMING = 1;

    private Context context;

    private List<Chat> chatList;

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public ChatWithUserAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MESSAGE_TYPE_SENT){

            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_sent_message, parent, false);
            return new ViewHolder(view);

        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_incoming_message, parent, false);
            return new ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = chatList.get(position);

        holder.messageTextView.setText(chat.getMessage());

        holder.timeTextView.setText(chat.getTime());
    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (chatList.get(position).getSender().equals(firebaseUser.getUid())){

            return MESSAGE_TYPE_SENT;
        } else {
            return MESSAGE_TYPE_INCOMING;
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.time)
        TextView timeTextView;

        @BindView(R.id.show_message)
        TextView messageTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
