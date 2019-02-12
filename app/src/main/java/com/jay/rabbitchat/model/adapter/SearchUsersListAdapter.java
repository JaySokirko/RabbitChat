package com.jay.rabbitchat.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jay.rabbitchat.R;
import com.jay.rabbitchat.model.firebasedatabase.user.User;
import com.jay.rabbitchat.view.chatscreen.ChatActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUsersListAdapter extends RecyclerView.Adapter<SearchUsersListAdapter.ViewHolder> {

    private Context context;

    private List<User> userList;

    private LayoutInflater layoutInflater;

    public SearchUsersListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;

        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public SearchUsersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycler_view_search_users, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchUsersListAdapter.ViewHolder holder, int position) {

        User user = userList.get(position);

        //set user image
        if (user.getImageURL().equals("default")) {

            holder.userProfileImage.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.ic_user_without_photo));
        } else {
            Glide.with(context).load(user.getImageURL()).into(holder.userProfileImage);
        }

        //set user name
        holder.userNameTextView.setText(user.getUserName());

        holder.itemView.setOnClickListener((View view) ->
                context.startActivity(new Intent(context, ChatActivity.class)
                .putExtra("userId", user.getId())
                .putExtra("userImageUrl", user.getImageURL())
                .putExtra("userName", user.getUserName())));

        //set user status (green - online, gray - offline)
        if (user.isOnline()){

            holder.userStatusImage.setBackgroundColor(context.getResources()
                    .getColor(R.color.colorGreen300));
        } else {
            holder.userStatusImage.setBackgroundColor(context.getResources()
                    .getColor(R.color.colorGray700));
        }
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image)
        CircleImageView userProfileImage;

        @BindView(R.id.user_name)
        TextView userNameTextView;

        @BindView(R.id.user_status)
        ImageView userStatusImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
