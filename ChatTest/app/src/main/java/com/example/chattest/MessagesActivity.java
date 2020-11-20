package com.example.chattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;

import com.example.chattest.Models.Chat;
import com.example.chattest.Adapter.MessageAdapater;
import com.example.chattest.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Message" ;
    FirebaseUser fbUser;
    DatabaseReference dbRef;
    Button btn;
    EditText et;
    Intent intent;
    RecyclerView rcv;
    List<Chat> ChatList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        intent = getIntent();
        final String userId = intent.getStringExtra("userid");
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        btn = findViewById(R.id.btn_msg_send);
        et = findViewById(R.id.et_msg_msg);
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        rcv = findViewById(R.id.rv_msg);
        rcv.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        linearLayout.setStackFromEnd(true);
        rcv.setLayoutManager(linearLayout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et.getText().toString();
                sendMessage(fbUser.getUid(),userId,message);
                et.setText("");
            }
        });
        et.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String message = et.getText().toString();
                    sendMessage(fbUser.getUid(),userId,message);
                    et.setText("");
                    return true;
                }
                return false;
            }
        });
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                ReadMessage(fbUser.getUid(),userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void sendMessage(String sender,String receiver, String message)
    {
       DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender" ,sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message" ,message);
        dbRef.child("Chats").push().setValue(hashMap);
    }
    private void ReadMessage(final String myId, final String userId)
    {
        ChatList = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChatList.clear();
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    Chat chat = snap.getValue(Chat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                        chat.getReceiver().equals(userId) && chat.getSender().equals(myId))
                    {
                        ChatList.add(chat);
                    }
                    MessageAdapater messageAdapater = new MessageAdapater(MessagesActivity.this,ChatList);
                    rcv.setAdapter(messageAdapater);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void status(String status)
    {
        fbUser= FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(fbUser.getUid());
        HashMap<String,Object> map = new HashMap<>();
        map.put("status",status);
        dbRef.updateChildren(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("Offline");
    }

}