package com.jay.rabbitchat.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jay.rabbitchat.R;
import com.jay.rabbitchat.model.firebasedatabase.user.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class InterlocutorsListAdapter extends RecyclerView.Adapter<InterlocutorsListAdapter.ViewHolder> {

    private Context context;
    private List<User> users;


    public InterlocutorsListAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_interlocutors_list,
                parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);

        if (user.getImageURL().equals("default")){

            holder.profileImage.setImageResource(R.drawable.ic_user_without_photo);
        } else {
            Glide.with(context).load(user.getImageURL()).into(holder.profileImage);
        }

        holder.userNameTextView.setText(user.getUserName());

        //todo last message & date
    }


    @Override
    public int getItemCount() {
        return users.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile_image)
        CircleImageView profileImage;

        @BindView(R.id.user_name)
        TextView userNameTextView;

        @BindView(R.id.last_message)
        TextView lastMessageTextView;

        @BindView(R.id.date)
        TextView dateTextView;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
