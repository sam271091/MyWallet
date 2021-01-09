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
    private LiveData<List<ValueItem>> valueItems;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = AppDataBase.getInstance(application);
        wallets = database.walletDao().getAllWallets();
        valueItems = database.walletDao().getAllValueItems();
    }



    //Wallets
    public LiveData<List<Wallet>> getWallets() {
        return wallets;
    }

    public void deleteAllWalets(){

    }

    public void insertWallet(Wallet wallet){
        new insertWalletTask().execute(wallet);
    }

    public void updateWallet(Wallet wallet){
        new UpdateWalletTask().execute(wallet);
    }

    public void deleteWallet(Wallet wallet){
        new DeleteWalletTask().execute(wallet);
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

    private static class UpdateWalletTask extends AsyncTask<Wallet,Void,Void>{
        @Override
        protected Void doInBackground(Wallet... wallets) {
            database.walletDao().updateWallet(wallets[0]);
            return null;
        }
    }


    private static class DeleteWalletTask extends AsyncTask<Wallet,Void,Void>{
        @Override
        protected Void doInBackground(Wallet... wallets) {
            database.walletDao().DeleteWallet(wallets[0]);
            return null;
        }
    }

    //

    //Value Items


    public LiveData<List<ValueItem>> getValueItems() {
        return valueItems;
    }

    public void deleteAllValueItems(){

    }

    public void insertValueItem(ValueItem valueItem){
        new insertValueItemTask().execute(valueItem);
    }

    public void updateValueItem(ValueItem valueItem){
        new UpdateValueItemTask().execute(valueItem);
    }

    public void deleteValueItem(ValueItem valueItem){
        new DeleteValueItemTask().execute(valueItem);
    }

    public void InsertValueItems(){

    }


    private static class insertValueItemTask extends AsyncTask<ValueItem,Void,Void>{
        @Override
        protected Void doInBackground(ValueItem... valueItems) {
            database.walletDao().insertValueItem(valueItems[0]);
            return null;
        }
    }

    private static class UpdateValueItemTask extends AsyncTask<ValueItem,Void,Void>{
        @Override
        protected Void doInBackground(ValueItem... valueItems) {
            database.walletDao().updateValueItem(valueItems[0]);
            return null;
        }
    }


    private static class DeleteValueItemTask extends AsyncTask<ValueItem,Void,Void>{
        @Override
        protected Void doInBackground(ValueItem... valueItems) {
            database.walletDao().DeleteValueItem(valueItems[0]);
            return null;
        }
    }


    //

}
