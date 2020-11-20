package com.example.chattest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.chattest.Fragments.ChatFragment;
import com.example.chattest.Fragments.UserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    Fragment frag;
    Fragment chatFragment;
    FragmentManager manager;
    FrameLayout fl;
    Button btn_message;
    Button btn_contacts;
    FragmentTransaction transaction;
    FirebaseUser Fuser;
    DatabaseReference reference;
    TextView tv;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_message = findViewById(R.id.btn_msg);
        btn_contacts = findViewById(R.id.btn_contact);
        frag = new UserFragment();
        chatFragment = new ChatFragment();
        fl = findViewById(R.id.frame_layout);
        btn_message.setOnClickListener(this);
        btn_contacts.setOnClickListener(this);
        LoadFragment(frag,"Chat");
        Fuser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onClick(View v)
    {
      switch (v.getId())
      {
          case R.id.btn_msg :
              LoadFragment(chatFragment,"message");
             // btn_message.setBackgroundColor(getColor(R.color.vert)); A IMPLEMENTER AVEC UNE IMAGEVIEW EN DESSOUS DE 1 SP
              // btn_contacts.setBackgroundColor(getColor(R.color.blanc));
              break;
          case R.id.btn_contact :
              LoadFragment(frag,"Chat");
              //  btn_message.setBackgroundColor(getColor(R.color.blanc));
              //  btn_contacts.setBackgroundColor(getColor(R.color.vert));
              break;
      }

    }

    public void LoadFragment(Fragment fragment, String string)
    {
        FragmentManager fm = getSupportFragmentManager();
        View fragmentContainer = findViewById(R.id.frame_layout);
        transaction = fm.beginTransaction();
        transaction.replace(fragmentContainer.getId(),fragment,string);
        transaction.addToBackStack(string);
        transaction.commit();
    }

    private void status(String status)
    {
        Fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(Fuser.getUid());
        HashMap<String,Object> map = new HashMap<>();
        map.put("status",status);
        reference.updateChildren(map);
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






