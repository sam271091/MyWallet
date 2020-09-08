package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mywallet.adapters.WalletsAdapter;

public class MainActivity extends AppCompatActivity {
    WalletsAdapter adapter;
    RecyclerView recyclerViewWallets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new WalletsAdapter();
        recyclerViewWallets = findViewById(R.id.RecyclerViewWallets);
        recyclerViewWallets.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWallets.setAdapter(adapter);



    }
}