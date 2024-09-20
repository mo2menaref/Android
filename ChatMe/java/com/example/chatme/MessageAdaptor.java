package com.example.chatme;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;
public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.MyViewHolder> {
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    private Context context;
    private List<Message_model> messageList;

    public MessageAdaptor(Context context) {
        this.context = context;
        this.messageList = new ArrayList<>();
    }
    public void add(Message_model message) {
        messageList.add(message);
        notifyItemInserted(messageList.size() - 1);
    }
    public void addAll(List<Message_model> messages) {
        messageList.addAll(messages);
        notifyDataSetChanged();
    }
    public void clear() {
        messageList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MessageAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.d("asdasdasdasdadsasd", String.valueOf(viewType));

        if (viewType == VIEW_TYPE_SENT) {
            View  view = LayoutInflater.from(context).inflate(R.layout.messagesend, parent, false);
            return new MessageAdaptor.MyViewHolder(view);

        } else {
            View  view = LayoutInflater.from(context).inflate(R.layout.messagereceive, parent, false);
            return new MessageAdaptor.MyViewHolder(view);
        }
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull MessageAdaptor.MyViewHolder parent, int position) {
        Message_model MessageModel = messageList.get(position);
        Log.d("erwersdfcxvxcv", messageList.get(position).getMessageId());
        Log.d("erwersdfcxvxcv", messageList.get(position).getSenderId());
        if (MessageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            parent.TextSendMessage.setText(MessageModel.getMessage());
        } else {
            parent.TextReceiveMeassge.setText(MessageModel.getMessage());
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }
    public List<Message_model> getmessageModelList() {
        return messageList;
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TextSendMessage, TextReceiveMeassge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TextSendMessage = itemView.findViewById(R.id.textviewsendmesssage);
            TextReceiveMeassge = itemView.findViewById(R.id.textviewreceivedmesssage);
        }
    }
}
