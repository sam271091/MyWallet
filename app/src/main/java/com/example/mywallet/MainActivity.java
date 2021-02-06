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

import com.example.mywallet.adapters.TransactionsAdapter;
import com.example.mywallet.adapters.WalletsAdapter;
import com.example.mywallet.adapters.WalletsPagerAdapter;
import com.example.mywallet.converters.WalletConverter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TransactionsAdapter adapter;
    RecyclerView recyclerViewWallets;
    MainViewModel viewModel;
    ViewPager2 walletViewPager;
    WalletsPagerAdapter pagerAdapter;
    Wallet currwallet;
    List<Wallet> walletsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("My wallet");

        walletsList = new ArrayList<>();

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);


        walletViewPager = findViewById(R.id.ViewPagerWallets);

        pagerAdapter = new WalletsPagerAdapter(this);

        adapter = new TransactionsAdapter();

        viewModel.getWallets().observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
//                adapter.setWallets(wallets);
                pagerAdapter.setWallets(wallets);
                walletsList.clear();
                walletsList .addAll(wallets);
//                int pagerPosition = walletViewPager.getCurrentItem();
                if (currwallet == null){
                    setCurrentWallet(0);
                } else {
                    setCurrentWallet();
                }


            }
        });





        adapter.setOnTransactionClickListener(new TransactionsAdapter.OnTransactionClickListener() {
            @Override
            public void OnTransactionClick(int position) {
                List<Transaction> transactions = adapter.getTransactions();
                Transaction currtransaction = transactions.get(position);
                OpenTransaction(currtransaction);
            }
        });

//        adapter.setOnWalletDeleteClickListener(new WalletsAdapter.OnWalletDeleteClickListener() {
//            @Override
//            public void OnWalletDeleteClick(final int position) {
//
//
//
//
//            }
//        });


        pagerAdapter.setOnWalletClickListener(new WalletsPagerAdapter.OnWalletClickListener() {
            @Override
            public void OnWalletClick(int position) {
                List<Wallet> wallets = pagerAdapter.getWallets();
                Wallet currwallet = wallets.get(position);
                openWallet(currwallet);
            }
        });

        pagerAdapter.setOnWalletDeleteClickListener(new WalletsPagerAdapter.OnWalletDeleteClickListener() {
            @Override
            public void OnWalletDeleteClick(final int position) {
                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.question_delete_wallet)
                        .setTitle(R.string.warning_title)
                        .setCancelable(true)
                        .setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<Wallet> wallets = pagerAdapter.getWallets();
                                Wallet currwallet = wallets.get(position);
                                viewModel.deleteWallet(currwallet);
                            }
                        })
                        .setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
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

        walletViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentWallet(position);

            }
        });




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


    public void onClickAdd(View view) {
        onOperationClick(Type.receipt);
    }


    void setCurrentWallet(){


        setTransactionObserver();


    }

     void setCurrentWallet(int pagerPosition){
//        int pagerPosition = walletViewPager.getCurrentItem();
        List<Wallet> wallets = pagerAdapter.getWallets();
        if (wallets.size() != 0){
             currwallet = wallets.get(pagerPosition);
         }


         setTransactionObserver();


    }

    void setTransactionObserver(){
        viewModel.getTransactionsByWallet(WalletConverter.WalletToString(currwallet)).observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                adapter.setTransactions(transactions);
                pagerAdapter.setWallets(walletsList);
            }
        });
    }



    void onOperationClick(Type type){
        int pagerPosition = walletViewPager.getCurrentItem();
        List<Wallet> wallets = pagerAdapter.getWallets();

       if (wallets.size() != 0){
           Wallet currwallet = wallets.get(pagerPosition);

           Intent intent = new Intent(this,transaction_item.class);
           intent.putExtra("wallet",currwallet);
           intent.putExtra("type",type);
           startActivity(intent);
       } else {
           Toast.makeText(this, R.string.warning_no_wallet_for_transaction, Toast.LENGTH_SHORT).show();
       }


    }


    void OpenTransaction(Transaction currTransaction){
        Intent intent = new Intent(this,transaction_item.class);
        intent.putExtra("currTransaction",currTransaction);
        startActivity(intent);
    }


    public void onClickMinus(View view) {
        onOperationClick(Type.expense);
    }

    public void onClickAnalytics(View view) {
        Intent intent = new Intent(this,activity_Charts.class);
        intent.putExtra("currWallet",currwallet);
        startActivity(intent);
    }
}