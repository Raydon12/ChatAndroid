package com.example.chattest.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class UserFragment extends Fragment {

    private RecyclerView rv;
    private UserAdapter ua;
    private List<User>lu;
    public static final String ARG_OBJECT = "Users";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user,container,false);
        rv = v.findViewById(R.id.recycler_frag);
        rv.setHasFixedSize(true) ;
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        lu = new ArrayList<>();
        readUser();
        return v;
    }
    private void readUser() {

        final FirebaseUser firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot)
            {
                lu.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);

                    assert user != null;
                    assert  firebaseUser != null;
                    if(!user.getId().equals(firebaseUser.getUid()))
                    {
                        lu.add(user);
                    }
                }
                ua = new UserAdapter(getContext(),lu,true);
                rv.setAdapter(ua);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}