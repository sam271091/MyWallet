package com.example.mywallet;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mywallet.data.AppDataBase;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

public class MainViewModel extends AndroidViewModel  {

    private static AppDataBase database;
    private LiveData<List<Wallet>> wallets;
    private LiveData<List<ValueItem>> valueItems;
    private LiveData<List<Counterparty>> counterparties;
    private LiveData<List<Transaction>> transactions;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = AppDataBase.getInstance(application);
        wallets = database.walletDao().getAllWallets();
        valueItems = database.walletDao().getAllValueItems();
        counterparties = database.walletDao().getAllCounterparties();
        transactions   = database.walletDao().getAllTransactions();
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


    public Double getCurrentBalance(String wallet){

        try {
            return new getCurrentBalanceTask().execute(wallet).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }



    public List<Transaction> getDataByWallet(String wallet){

        try {
            return new getDataByWalletTask().execute(wallet).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Transaction> getDataByWalletAndCounterparty(String wallet,String counterparty){

        try {
            return new getDataByWalletAndCounterpartyTask().execute(wallet,counterparty).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static class getDataByWalletAndCounterpartyTask extends AsyncTask<String,Void,List<Transaction>>{
        @Override
        protected List<Transaction> doInBackground(String... strings) {
            return database.walletDao().getDataByWalletAndCounterparty(strings[0],strings[1]);
        }
    }


    private static class getDataByWalletTask extends AsyncTask<String,Void,List<Transaction>>{
        @Override
        protected List<Transaction> doInBackground(String... wallets) {
            return database.walletDao().getDataByWallet(wallets[0]);
        }
    }




    private static class getCurrentBalanceTask extends AsyncTask<String,Void,Double>{
        @Override
        protected Double doInBackground(String... wallets) {
            return database.walletDao().getCurrentBalance(wallets[0]);
        }
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




    //Counterparties


    public LiveData<List<Counterparty>> getCounterparties() {
        return counterparties;
    }

    public void deleteAllCounterparties(){

    }


    public void insertCounterparty(Counterparty counterparty){
        new insertCounterpartyTask().execute(counterparty);
    }

    public void updateCounterparty(Counterparty counterparty){
        new UpdateCounterpartyTask().execute(counterparty);
    }

    public void deleteCounterparty(Counterparty counterparty){
        new DeleteCounterpartyTask().execute(counterparty);
    }

    public void InsertCounterparties(){

    }


    private static class insertCounterpartyTask extends AsyncTask<Counterparty,Void,Void>{
        @Override
        protected Void doInBackground(Counterparty... counterparties) {
            database.walletDao().insertCounterparty(counterparties[0]);
            return null;
        }
    }

    private static class UpdateCounterpartyTask extends AsyncTask<Counterparty,Void,Void>{
        @Override
        protected Void doInBackground(Counterparty... counterparties) {
            database.walletDao().updateCounterparty(counterparties[0]);
            return null;
        }
    }


    private static class DeleteCounterpartyTask extends AsyncTask<Counterparty,Void,Void>{
        @Override
        protected Void doInBackground(Counterparty... counterparties) {
            database.walletDao().DeleteCounterparty(counterparties[0]);
            return null;
        }
    }


    //


    //Transactions


    public LiveData<List<Transaction>> getTransactions() {
        return transactions;
    }

    public LiveData<List<Transaction>> getTransactionsByWallet(String wallet){
        try {
            transactions = new GetTransactionsByWalletTask().execute(wallet).get();
            return transactions;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteAllTransactions(){

    }

    public void insertTransaction(Transaction transaction){
        new insertTransactionTask().execute(transaction);
    }

    public void updateTransaction(Transaction transaction){
        new UpdateTransactionTask().execute(transaction);
    }

    public void deleteTransaction(Transaction transaction){
        new DeleteTransactionTask().execute(transaction);
    }

    public void InsertTransactions(){

    }


    private static class insertTransactionTask extends AsyncTask<Transaction,Void,Void>{
        @Override
        protected Void doInBackground(Transaction... transactions) {
            database.walletDao().insertTransaction(transactions[0]);
            return null;
        }
    }

    private static class UpdateTransactionTask extends AsyncTask<Transaction,Void,Void>{
        @Override
        protected Void doInBackground(Transaction... transactions) {
            database.walletDao().updateTransaction(transactions[0]);
            return null;
        }
    }


    private static class DeleteTransactionTask extends AsyncTask<Transaction,Void,Void>{
        @Override
        protected Void doInBackground(Transaction... transactions) {
            database.walletDao().DeleteTransaction(transactions[0]);
            return null;
        }
    }

    private static class GetTransactionsByWalletTask extends AsyncTask<String,Void,LiveData<List<Transaction>>>{
        @Override
        protected LiveData<List<Transaction>> doInBackground(String... wallets) {
            return database.walletDao().getAllTransactionsByWallet(wallets[0]);
        }
    }


    //

}
