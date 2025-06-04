package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsapp.Adapter.ChatAdapter;
import com.example.whatsapp.Modles.MessageModel;
import com.example.whatsapp.databinding.ActivityMygroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MygroupChat extends AppCompatActivity {

    private EditText massagebox;
    private RecyclerView recyclerView;
    private ImageView sendme;
    private TextView name;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygroup_chat);
        getSupportActionBar().hide();

        final ArrayList<MessageModel> massagemodel = new ArrayList<>();
        final String senderId = FirebaseAuth.getInstance().getUid();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        massagebox = findViewById(R.id.etMessage);
        recyclerView = findViewById(R.id.chatRecylearId);
        sendme = findViewById(R.id.sendmeid);
        name = findViewById(R.id.usernameDetail);


        final ChatAdapter adapter = new ChatAdapter(massagemodel, this);
        recyclerView.setAdapter(adapter);
        name.setText("Friends Chat");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        database.getReference().child("groupchat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                massagemodel.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    massagemodel.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = massagebox.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                massagebox.setText("");
                database.getReference().child("groupchat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });




            }



        });
    }

}