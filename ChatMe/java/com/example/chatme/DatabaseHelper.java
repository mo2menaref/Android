package com.example.chatme;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {
    private static final String TAG = "DatabaseHelper";

    public static void initializeChatPaths(String senderRoom, String receiverRoom, InitializationCompleteListener listener) {
        Log.d(TAG, "Initializing chat paths for sender: " + senderRoom + ", receiver: " + receiverRoom);
        DatabaseReference dbReferenceSender = FirebaseDatabase.getInstance().getReference().child("Chats").child(senderRoom);
        DatabaseReference dbReferenceReceiver = FirebaseDatabase.getInstance().getReference().child("Chats").child(receiverRoom);

        dbReferenceSender.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "Sender room check completed. Exists: " + dataSnapshot.exists());
                if (!dataSnapshot.exists()) {
                    initializeRoom(dbReferenceSender, "sender", () -> checkReceiverRoom(dbReferenceReceiver, listener));
                } else {
                    checkReceiverRoom(dbReferenceReceiver, listener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Sender room check failed", databaseError.toException());
                listener.onInitializationComplete(false);
            }
        });
    }

    private static void checkReceiverRoom(DatabaseReference dbReferenceReceiver, InitializationCompleteListener listener) {
        dbReferenceReceiver.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "Receiver room check completed. Exists: " + dataSnapshot.exists());
                if (!dataSnapshot.exists()) {
                    initializeRoom(dbReferenceReceiver, "receiver", () -> listener.onInitializationComplete(true));
                } else {
                    listener.onInitializationComplete(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Receiver room check failed", databaseError.toException());
                listener.onInitializationComplete(false);
            }
        });
    }

    private static void initializeRoom(DatabaseReference roomReference, String roomType, Runnable onComplete) {
        Map<String, Object> roomData = new HashMap<>();
        roomData.put("initialized", true);
        roomData.put("timestamp", ServerValue.TIMESTAMP);
        roomReference.setValue(roomData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, roomType + " room initialized successfully");
                onComplete.run();
            } else {
                Log.e(TAG, "Failed to initialize " + roomType + " room", task.getException());
                onComplete.run();  // Still continue, as the room might have been created by another concurrent operation
            }
        });
    }

    public interface InitializationCompleteListener {
        void onInitializationComplete(boolean success);
    }
}