package com.example.bakelsfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
//    TextView logout;
    FirebaseAuth mAuth;

    private static String TAG = "RECYCLER_PRODUCT";
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    private FloatingActionButton sqlite_btn,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.log_out);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ProductModel> options =
                new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Data"),ProductModel.class)
                        .build();

        productAdapter = new ProductAdapter(options);
        recyclerView.setAdapter(productAdapter);

        sqlite_btn = findViewById(R.id.sqlite_list_btn);

        sqlite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListSQLiteActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        productAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        productAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String text) {
        FirebaseRecyclerOptions<ProductModel> searchOptions =
                new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Data").orderByChild("name").startAt(text.toUpperCase(Locale.ROOT)).endAt("~"),ProductModel.class)
                        .build();
        productAdapter = new ProductAdapter(searchOptions);
        productAdapter.startListening();
        recyclerView.setAdapter(productAdapter);
    }



}