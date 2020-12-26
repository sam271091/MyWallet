package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

        adapter.setOnWalletClickListener(new WalletsAdapter.OnWalletClickListener() {
            @Override
            public void OnWalletClick(int position) {
                List<Wallet> wallets = adapter.getWallets();
                Wallet currwallet = wallets.get(position);
                openWallet(currwallet);
            }
        });

        adapter.setOnWalletDeleteClickListener(new WalletsAdapter.OnWalletDeleteClickListener() {
            @Override
            public void OnWalletDeleteClick(int position) {
                List<Wallet> wallets = adapter.getWallets();
                Wallet currwallet = wallets.get(position);
                viewModel.deleteWallet(currwallet);
            }
        });


        recyclerViewWallets = findViewById(R.id.RecyclerViewWallets);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewWallets.setLayoutManager(manager);
        recyclerViewWallets.setAdapter(adapter);



    }

    public void onClickCreateWallet(View view) {
        openWallet();
    }


    private void openWallet(){
        Intent intent = new Intent(this,Wallet_Item.class);
        startActivity(intent);
    }

    private void openWallet(Wallet wallet){
        Intent intent = new Intent(this,Wallet_Item.class);
        intent.putExtra("wallet",wallet);
        startActivity(intent);
    }



}