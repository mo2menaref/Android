package com.example.chatme;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatme.Login.About;
import com.example.chatme.Login.Login;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
public class HomePage extends AppCompatActivity {
    Context context;
    RecyclerView recyclerview;
    DatabaseReference databaseReference;
    UserAdaptor usersadaptor;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerview = findViewById(R.id.recycler);
        logout = findViewById(R.id.baseline_logout_24);
        FirebaseApp.initializeApp(this);
        setSupportActionBar(toolbar);
        context = this;
        String name = getIntent().getStringExtra("User");
        getSupportActionBar().setTitle(name);
        usersadaptor = new UserAdaptor(context);

        recyclerview.setAdapter(usersadaptor);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersadaptor.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userId = dataSnapshot.getKey();
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    if (user != null && user.getId() != null && !user.getId().equals(FirebaseAuth.getInstance().getUid())) {
                        usersadaptor.add(user);
                    }
                }
                List<UserModel> list = usersadaptor.getUserModellist();
                usersadaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("dfgdfgertert","200");            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(context, Login.class));
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(HomePage.this, About.class);
            startActivity(intent);
            return true;
        } else {
            return false;
        }

    }
}
