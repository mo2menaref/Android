package com.example.chatme;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatPage extends AppCompatActivity {
    private static final String TAG = "ChatPage";
    private String receiverid, receivername, senderroom, receiverroom;
    private DatabaseReference dbReferenceSender, dbReferenceReceiver, userReference;
    private ImageView send;
    private EditText message;
    private RecyclerView recycleView;
    private MessageAdaptor messageadaptor;
    private boolean isInitialized = false;
    private ValueEventListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        initializeViews();
        setupFirebase();
        setupChatRooms();
        setupSendButton();
    }

    private void initializeViews() {
        send = findViewById(R.id.SendIcon);
        message = findViewById(R.id.SendBar);
        recycleView = findViewById(R.id.recycle1);
        messageadaptor = new MessageAdaptor(this);
        recycleView.setAdapter(messageadaptor);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbarplay);
        setSupportActionBar(toolbar);
    }

    private void setupFirebase() {
        FirebaseApp.initializeApp(this);
        userReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    private void setupChatRooms() {
        receiverid = getIntent().getStringExtra("id");
        receivername = getIntent().getStringExtra("name");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(receivername);
        }
        String currentUserId = FirebaseAuth.getInstance().getUid();
        if (currentUserId != null && receiverid != null) {
            senderroom = currentUserId + receiverid;
            receiverroom = receiverid + currentUserId;
            initializeChatPaths();
        } else {
            Log.e(TAG, "Current user ID or receiver ID is null");
            Toast.makeText(this, "Error setting up chat. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializeChatPaths() {
        DatabaseHelper.initializeChatPaths(senderroom, receiverroom, success -> {
            if (success) {
                dbReferenceSender = FirebaseDatabase.getInstance().getReference().child("Chats").child(senderroom);
                dbReferenceReceiver = FirebaseDatabase.getInstance().getReference().child("Chats").child(receiverroom);
                setupMessageListener();
                isInitialized = true;
            } else {
                Log.e(TAG, "Failed to initialize chat paths");
                Toast.makeText(ChatPage.this, "Failed to initialize chat. Please try again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setupSendButton() {
        send.setOnClickListener(view -> {
            try {
                if (isInitialized) {
                    String msg = message.getText().toString().trim();
                    if (!msg.isEmpty()) {
                        sendMessage(msg);
                    } else {
                        Toast.makeText(ChatPage.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Attempted to send message before initialization");
                    Toast.makeText(ChatPage.this, "Chat is not ready yet. Please wait.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in setupSendButton", e);
                Toast.makeText(ChatPage.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupMessageListener() {
        if (messageListener != null) {
            dbReferenceSender.removeEventListener(messageListener);
        }
        messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    List<Message_model> messageModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Object value = dataSnapshot.getValue();
                        if (value instanceof Map) {
                            Message_model messageModel = dataSnapshot.getValue(Message_model.class);
                            if (messageModel != null) {
                                messageModelList.add(messageModel);
                            } else {
                                Log.w(TAG, "Skipping null message model");
                            }
                        } else {
                            Log.w(TAG, "Unexpected value type: " + value);
                        }
                    }
                    // Sort messages by timestamp
                    Collections.sort(messageModelList, Comparator.comparingLong(Message_model::getTimestamp));
                    messageadaptor.clear();
                    messageadaptor.addAll(messageModelList);
                    messageadaptor.notifyDataSetChanged();
                    if (!messageModelList.isEmpty()) {
                        recycleView.scrollToPosition(messageModelList.size() - 1);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error processing messages", e);
                    Toast.makeText(ChatPage.this, "Error loading messages", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read messages", error.toException());
                Toast.makeText(ChatPage.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
            }
        };
        dbReferenceSender.addValueEventListener(messageListener);
    }


    private void sendMessage(String messageText) {
        try {
            if (dbReferenceSender == null || dbReferenceReceiver == null) {
                Log.e(TAG, "Database references are not initialized");
                Toast.makeText(this, "Unable to send message. Please try again later.", Toast.LENGTH_SHORT).show();
                return;
            }
            String messageid = UUID.randomUUID().toString();
            String currentUserId = FirebaseAuth.getInstance().getUid();
            if (currentUserId == null) {
                Log.e(TAG, "Current user ID is null");
                Toast.makeText(this, "Error sending message. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }
            long timestamp = System.currentTimeMillis();
            Message_model messageModel = new Message_model(messageid, currentUserId, messageText, timestamp);
            Map<String, Object> updates = new HashMap<>();
            updates.put("/Chats/" + senderroom + "/" + messageid, messageModel);
            updates.put("/Chats/" + receiverroom + "/" + messageid, messageModel);
            FirebaseDatabase.getInstance().getReference().updateChildren(updates)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Message sent successfully to both rooms");
                        message.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to send message", e);
                        Toast.makeText(ChatPage.this, "Failed to send message. Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Log.e(TAG, "Error in sendMessage", e);
            Toast.makeText(this, "An error occurred while sending the message. Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbReferenceSender != null && messageListener != null) {
            dbReferenceSender.removeEventListener(messageListener);
        }
    }
}
