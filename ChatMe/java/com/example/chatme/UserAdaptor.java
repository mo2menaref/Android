package com.example.chatme;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.MyViewHolder> {
    private Context context;
    private List<UserModel> userModellist;

    public UserAdaptor(Context context) {
        this.context = context;
        this.userModellist = new ArrayList<>();
    }

    public void add(UserModel userModel) {
        userModellist.add(userModel);
        notifyItemInserted(userModellist.size() - 1);
    }

    public void clear() {
        userModellist.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_users_models, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdaptor.MyViewHolder parent, int position) {
        UserModel userModel = userModellist.get(position);
            parent.Name.setText(userModel.getName());
            parent.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatPage.class);
                    intent.putExtra("id", userModel.getId());
                    intent.putExtra("name", userModel.getName());
                    context.startActivity(intent);
                }
            });

    }

    public List<UserModel> getUserModellist() {
        return userModellist;
    }

    @Override
    public int getItemCount() {
        return userModellist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Username);
        }
    }
}
