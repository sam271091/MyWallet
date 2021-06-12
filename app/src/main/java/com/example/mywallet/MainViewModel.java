package com.example.mywallet;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mywallet.data.AppDataBase;

import java.io.Serializable;
import java.util.Date;
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
        valueItems = database.walletDao().getValueItems();
        counterparties = database.walletDao().getCounterparties();
        transactions   = database.walletDao().getTransactions();
    }




    //Wallets

    public List<Wallet> getListOfWallets(){
        try {
            return new getWalletsTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

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


    public Double getTotalByType(String wallet, String type){

        try {
            getTotalByTypeTask classObject = new getTotalByTypeTask();
            classObject.setWallet(wallet);
            classObject.setType(type);
            return classObject.execute().get();
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



    public List<Transaction> getDataByWalletAndType(String wallet, String type, Long from,Long to){

        try {
            getDataByWalletAndTypeTask classObject = new getDataByWalletAndTypeTask();
            classObject.setWallet(wallet);
            classObject.setType(type);
            classObject.setFrom(from);
            classObject.setTo(to);
            return classObject.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Transaction> getDataByWalletAndType_ValueItems(String wallet, String type, Long from,Long to){

        try {
            getDataByWalletAndType_ValueItemsTask classObject = new getDataByWalletAndType_ValueItemsTask();
            classObject.setWallet(wallet);
            classObject.setType(type);
            classObject.setFrom(from);
            classObject.setTo(to);
            return classObject.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }



    public List<Transaction> getDataByWalletGroupType(String wallet, Long from,Long to){

        try {
            getDataByWalletGroupTypeTask classObject = new getDataByWalletGroupTypeTask();
            classObject.setWallet(wallet);
            classObject.setFrom(from);
            classObject.setTo(to);
            return classObject.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Wallet getWalletbyId(String id){

        try {
            return  new getWalletbyIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static class getWalletsTask extends AsyncTask<Void,Void,List<Wallet>>{
        @Override
        protected List<Wallet> doInBackground(Void... voids) {
            return database.walletDao().getWallets();
        }
    }


    private static class getWalletbyIdTask extends AsyncTask<String,Void,Wallet>{
        @Override
        protected Wallet doInBackground(String... strings) {
            return database.walletDao().getWalletbyId((strings[0]));
        }
    }





    private static class getDataByWalletAndTypeTask extends AsyncTask<Void,Void,List<Transaction>>{
        private String wallet;
        private String type;
        private Long from;
        private Long to;

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        @Override
        protected List<Transaction> doInBackground(Void... voids) {
            return database.walletDao().getDataByWalletAndType(wallet,type,from,to);
        }
    }

    private static class getDataByWalletAndType_ValueItemsTask extends AsyncTask<Void,Void,List<Transaction>>{
        private String wallet;
        private String type;
        private Long from;
        private Long to;

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        @Override
        protected List<Transaction> doInBackground(Void... voids) {
            return database.walletDao().getDataByWalletAndType_ValueItems(wallet,type,from,to);
        }
    }

    private static class  getDataByWalletGroupTypeTask extends AsyncTask<Void,Void,List<Transaction>>{
        private String wallet;
        private Long from;
        private Long to;

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        @Override
        protected List<Transaction> doInBackground(Void... voids) {
            return  database.walletDao().getDataByWalletGroupType(wallet,from,to);
        }
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

    private static class getTotalByTypeTask extends AsyncTask<Void,Void,Double>{
        private String wallet;
        private String type;

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            return database.walletDao().getTotalByType(wallet,type);
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


    public List<ValueItem> getAllValueItems() {
        try {
            return new GetAllValueItemsTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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


    public static class GetAllValueItemsTask extends AsyncTask<Void,Void,List<ValueItem>>{
        @Override
        protected List<ValueItem> doInBackground(Void... voids) {
            return database.walletDao().getAllValueItems();
        }
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

    public List<Counterparty> getAllCounterparties(){
        try {
            return new  getAllCounterpartiesTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static class getAllCounterpartiesTask extends AsyncTask<Void,Void,List<Counterparty>>{
        @Override
        protected List<Counterparty> doInBackground(Void... voids) {
            return database.walletDao().getAllCounterparties();
        }
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


    public void deleteTransactionsByWallet(String wallet){
        new DeleteTransactionsByWalletTask().execute(wallet);
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


    public void updateTransactionsByWallet(String wallet,String newWallet){
        updateTransactionsByWalletTask classObject = new updateTransactionsByWalletTask();
        classObject.setWallet(wallet);
        classObject.setNewWallet(newWallet);
        classObject.execute();
    }


    public void updateTransactionsByValueItem(String valueItem,String newValueItem){
        updateTransactionsByValueItemTask classObject = new updateTransactionsByValueItemTask();
        classObject.setValueItem(valueItem);
        classObject.setNewValueItem(newValueItem);
        classObject.execute();
    }


    public void updateTransactionsByCounterparty(String counterparty,String newCounterparty){
        updateTransactionsByCounterpartyTask classObject = new updateTransactionsByCounterpartyTask();
        classObject.setCounterparty(counterparty);
        classObject.setNewCounterparty(newCounterparty);
        classObject.execute();
    }


    public List<Transaction> getAllTransactions (){
        try {
            return new getAllTransactionsTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public LiveData<List<Transaction>> getTransactionsByWalletForThePeriod(String wallet,Long from,Long to){
        getTransactionsByWalletForThePeriodTask classObject = new getTransactionsByWalletForThePeriodTask();
        classObject.setWallet(wallet);
        classObject.setFrom(from);
        classObject.setTo(to);

        try {
            return classObject.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class getTransactionsByWalletForThePeriodTask extends AsyncTask<Void,Void,LiveData<List<Transaction>>>{
        private String wallet;
        private Long from;
        private Long to;

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        @Override
        protected LiveData<List<Transaction>> doInBackground(Void... voids) {
            return database.walletDao().getAllTransactionsByWalletForThePeriod(wallet,from,to);
        }
    }


    private static class getAllTransactionsTask extends AsyncTask<Void,Void,List<Transaction>>{
        @Override
        protected List<Transaction> doInBackground(Void... voids) {
            return database.walletDao().getAllTransactions();
        }
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

    private static class DeleteTransactionsByWalletTask extends AsyncTask<String,Void,LiveData<List<Transaction>>>{
        @Override
        protected LiveData<List<Transaction>> doInBackground(String... strings) {
            database.walletDao().deleteTransactionByWallet(strings[0]);
            return null;
        }
    }

    private static class updateTransactionsByWalletTask extends AsyncTask<Void,Void,Void>{
        private String wallet;
        private String newWallet;

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public void setNewWallet(String newWallet) {
            this.newWallet = newWallet;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.walletDao().updateTransactionsByWallet(wallet,newWallet);
            return null;
        }
    }



    private static class updateTransactionsByValueItemTask extends AsyncTask<Void,Void,Void>{
        private String valueItem;
        private String newValueItem;


        public void setValueItem(String valueItem) {
            this.valueItem = valueItem;
        }

        public void setNewValueItem(String newValueItem) {
            this.newValueItem = newValueItem;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.walletDao().updateTransactionsByValueItem(valueItem,newValueItem);
            return null;
        }
    }


    private static class updateTransactionsByCounterpartyTask extends AsyncTask<Void,Void,Void>{
        private String counterparty;
        private String newCounterparty;


        public void setCounterparty(String counterparty) {
            this.counterparty = counterparty;
        }

        public void setNewCounterparty(String newCounterparty) {
            this.newCounterparty = newCounterparty;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.walletDao().updateTransactionsByCounterparty(counterparty,newCounterparty);
            return null;
        }
    }

    //

}
