package com.example.chattest.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chattest.Adapter.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Odbmess = database.getReference("Users");
    public UserDAO(){}

    public void Write(String message)
    {
        Odbmess.setValue(message);
    }
    public List<User> Read(final UserAdapter adapter)
    {
        final List<User> lus = new ArrayList<>();

        Odbmess.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lus.add(snapshot.getValue(User.class));
                Log.d("test", "Value is: " + snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return lus;
    }

}
