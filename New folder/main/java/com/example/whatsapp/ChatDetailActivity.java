package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.Adapter.ChatAdapter;
import com.example.whatsapp.Modles.MessageModel;
import com.example.whatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();





        final String senderId=auth.getUid();
        String reciveId=getIntent().getStringExtra("UserId");
        String userName= getIntent().getStringExtra("UserName");
        String profilePicture=getIntent().getStringExtra("profilePic");




        final ArrayList<MessageModel>  messageModels=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messageModels, this,reciveId);
        binding.chatRecylearId.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecylearId.setLayoutManager(layoutManager);

        binding.usernameDetail.setText(userName);
        Picasso.get().load(profilePicture).placeholder(R.drawable.user).into(binding.profileImage);

        binding.backArror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        final String senderRoom=senderId + reciveId;
        final String reciverRoom=reciveId + senderId;



        database.getReference().child("chats")
                .child(reciverRoom)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot  snapshot1:snapshot.getChildren()){
                    MessageModel model=snapshot1.getValue(MessageModel.class);
                    model.setMessageid(snapshot1.getKey());

                    messageModels.add(model);

                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });









        binding.sendId.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message= binding.etMessage.getText().toString();
                final MessageModel model=new MessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");



                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("chats")
                                .child(reciverRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });
            }
        });



    }
}