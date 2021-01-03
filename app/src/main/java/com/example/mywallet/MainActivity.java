package com.example.mywallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mywallet.adapters.WalletsAdapter;
import com.example.mywallet.adapters.WalletsPagerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WalletsAdapter adapter;
    RecyclerView recyclerViewWallets;
    MainViewModel viewModel;
    ViewPager2 walletViewPager;
    WalletsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);


        walletViewPager = findViewById(R.id.ViewPagerWallets);

        pagerAdapter = new WalletsPagerAdapter(this);

        adapter = new WalletsAdapter();

        viewModel.getWallets().observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                adapter.setWallets(wallets);
                pagerAdapter.setWallets(wallets);
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
            public void OnWalletDeleteClick(final int position) {


                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Do you really want to delete the wallet?")
                        .setTitle("Warning")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      List<Wallet> wallets = adapter.getWallets();
                      Wallet currwallet = wallets.get(position);
                      viewModel.deleteWallet(currwallet);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

               // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        recyclerViewWallets = findViewById(R.id.RecyclerViewWallets);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewWallets.setLayoutManager(manager);
        recyclerViewWallets.setAdapter(adapter);



        walletViewPager.setAdapter(pagerAdapter);




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