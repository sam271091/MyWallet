package com.example.mywallet;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mywallet.data.AppDataBase;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

public class MainViewModel extends AndroidViewModel  {

    private static AppDataBase database;
    private LiveData<List<Wallet>> wallets;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = AppDataBase.getInstance(application);
        wallets = database.walletDao().getAllWallets();
    }



    public LiveData<List<Wallet>> getWallets() {
        return wallets;
    }

    public void deleteAllWalets(){

    }

    public void insertWallet(Wallet wallet){
        new insertWalletTask().execute(wallet);
    }


    public void InsertWallets(){

    }


    private static class insertWalletTask extends AsyncTask<Wallet,Void,Void>{
        @Override
        protected Void doInBackground(Wallet... wallets) {
            database.walletDao().insertWallet(wallets[0]);
            return null;
        }
    }


}
