package com.example.chattest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattest.Models.Chat;
import com.example.chattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapater extends RecyclerView.Adapter<MessageAdapater.ViewHolder> {
    private final int MSG_RIGHT = 0;
    private final int MSG_LEFT = 1;
    private Context context;
    private List<Chat> ChatList;
    FirebaseUser Fuser;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    Intent intent;

    public MessageAdapater(Context context, List<Chat> ChatList) {
        this.context = context;
        this.ChatList = ChatList;
    }

    @NonNull
    @Override
    public MessageAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_LEFT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_left,parent,false);
            return  new MessageAdapater.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_right,parent,false);
            return  new MessageAdapater.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapater.ViewHolder holder, int position) {

        Chat chat = ChatList.get(position);
        if(chat.getMessage() != null)
        {
            holder.ShowMessage.setText(chat.getMessage());
        }
        else
            Log.d("erreur affiche chat","erreur");

    }

    @Override
    public int getItemCount() {
        return ChatList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ShowMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ShowMessage = itemView.findViewById(R.id.tv_msg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(ChatList.get(position).getSender().equals(Fuser.getUid()))
        {
            return MSG_RIGHT;
        }
        else
            return MSG_LEFT;
    }
}