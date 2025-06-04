package com.example.whatsapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Modles.MessageModel;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{
    ArrayList<MessageModel> messageModels;
    Context context;
    String reId;
    int SENDER_VIEW_TYPE=1;
    int RECIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;

    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String reId) {
        this.messageModels = messageModels;
        this.context = context;
        this.reId = reId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciver,parent,false);
            return new ReciverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel=messageModels.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are you want to delete this message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String sender=FirebaseAuth.getInstance().getUid() + reId;
                                database.getReference().child("chats").child("senderRoom").child(messageModel.getMessageid()).setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                return false;
            }
        });

        if (holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMgs.setText(messageModel.getMessage());
        }
        else{
            ((ReciverViewHolder)holder).reciverMsg.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId()==(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECIVER_VIEW_TYPE;
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public  class ReciverViewHolder extends RecyclerView.ViewHolder{
        TextView reciverMsg, reciverTime;

        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            reciverMsg=itemView.findViewById(R.id.reciverTextId);
            reciverTime=itemView.findViewById(R.id.reciverTime);
        }
    }
    public  class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMgs, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMgs=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }
}
