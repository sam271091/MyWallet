package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mywallet.adapters.WalletsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WalletsAdapter adapter;
    RecyclerView recyclerViewWallets;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);


        adapter = new WalletsAdapter();

        viewModel.getWallets().observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                adapter.setWallets(wallets);
            }
        });



        recyclerViewWallets = findViewById(R.id.RecyclerViewWallets);
        recyclerViewWallets.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWallets.setAdapter(adapter);



    }

    public void onClickCreateWallet(View view) {

        Intent intent = new Intent(this,Wallet_Item.class);
        startActivity(intent);
    }
}