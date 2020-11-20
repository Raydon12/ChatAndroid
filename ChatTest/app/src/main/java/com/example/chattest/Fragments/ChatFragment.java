package com.example.chattest.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chattest.Models.Chat;
import com.example.chattest.Models.User;
import com.example.chattest.Adapter.UserAdapter;
import com.example.chattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> mUsers;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    List<String> list_user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = v.findViewById(R.id.recycler_frag_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        list_user = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_user.clear();
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    Chat chat = snap.getValue(Chat.class);
                    assert chat != null;
                    if(chat.getSender().equals(firebaseUser.getUid()))
                    {
                        list_user.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid()))
                    {
                        list_user.add(chat.getSender());
                    }

                }

                ReadChat();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;

    }

    private void ReadChat() {
        mUsers = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot snapshot : datasnapshot.getChildren())
                {
                       User user = snapshot.getValue(User.class);

                    for(String id : list_user){
                        if(id != firebaseUser.getUid()){
                            if(user.getId().equals(id)){
                                if(mUsers.size() !=0){
                                    for(User user1 : mUsers){
                                        if(!user.getId().equals(user1.getId())){
                                            mUsers.add(user);
                                        }
                                    }
                                } else{
                                    mUsers.add(user);
                                }
                            }
                        }

                    }

                }

                userAdapter = new UserAdapter(getContext(),mUsers,true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public List<User> Remplissage(List<String> stringList, User user)
    {
        List<User>list = new ArrayList<>();


        return list;
    }

}