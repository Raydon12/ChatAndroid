package com.example.chattest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattest.MessagesActivity;
import com.example.chattest.Models.User;
import com.example.chattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;
    public Intent getIntent() {
        return intent;
    }
    public boolean isChat;
    FirebaseUser fbus;

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    Intent intent;

    public UserAdapter(Context context, List<User> userList,boolean isChat) {
        this.context = context;
        this.userList = userList;
        this.isChat = isChat;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_adapter,parent,false);
        return  new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User user = userList.get(position);
        fbus = FirebaseAuth.getInstance().getCurrentUser();
        holder.pseudo.setText(user.getPseudo());
        holder.cm.setImageResource(R.mipmap.ic_launcher);
        if(user.getStatus()!=null)
        {
            if(isChat)
            {
                holder.cmOff.setVisibility(View.GONE);
                holder.cmOn.setVisibility(View.GONE);
                if(user.getStatus().equals("Online") && user.getStatus() != null && user.getStatus() != fbus.getUid() )
                {
                    holder.cmOn.setVisibility(View.VISIBLE);
                    holder.cmOff.setVisibility(View.GONE);
                }
                else
                {
                    holder.cmOn.setVisibility(View.GONE);
                    holder.cmOff.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                holder.cmOn.setVisibility(View.GONE);
                holder.cmOff.setVisibility(View.GONE);
            }
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, MessagesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView pseudo;
        public CircleImageView cm;
        public CircleImageView cmOn;
        public CircleImageView cmOff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pseudo = itemView.findViewById(R.id.pseudo);
            cm = itemView.findViewById(R.id.circleImageView);
            cmOn = itemView.findViewById(R.id.img_on);
            cmOff = itemView.findViewById(R.id.img_off);
        }
    }


}
