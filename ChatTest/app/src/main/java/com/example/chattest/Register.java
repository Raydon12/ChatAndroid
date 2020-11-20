package com.example.chattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener {
    Button btnok;
    Button btnbck;
    EditText mail;
    EditText pwd;
    EditText pseudo;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnok = findViewById(R.id.btn_register_ok);
        btnbck = findViewById(R.id.btn_register_back);
        mail = findViewById(R.id.et_register_mail);
        pwd = findViewById(R.id.et_register_pwd);
        pseudo = findViewById(R.id.et_register_pseudo);
        btnok.setOnClickListener(this);
        btnbck.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View v) {
        String txt_pseudo = pseudo.getText().toString();
        String txt_mail = mail.getText().toString();
        String txt_pwd = pwd.getText().toString();
        switch (v.getId())
        {
            case R.id.btn_register_ok:
                register(txt_pseudo,txt_mail,txt_pwd);
                return;
            case R.id.btn_register_back :
                
                Back();
                return;
        }
    }

    private void Back() {
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finish();
    }

    private void register(final String pseudo, String mail , String pwd) {
        auth.createUserWithEmailAndPassword(mail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userId);
                    hashMap.put("pseudo",pseudo);
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast toast = Toast.makeText(getApplicationContext(),"Bienvenue ! /n votre compte est créer",Toast.LENGTH_LONG);
                                toast.show();
                                Intent intent = new Intent(Register.this,MainActivity.class);
                                startActivity(intent);
                                //finish();
                            }
                            else
                            {
                                Toast toast = Toast.makeText(getApplicationContext(),"ERREUR ! /n Frere c'est pas compliqué",Toast.LENGTH_LONG);
                                toast.show();

                            }
                        }
                    });
                }
                
            }
        });

    }
}