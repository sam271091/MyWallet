package com.example.mywallet.data;

import com.example.mywallet.Counterparty;
import com.example.mywallet.Transaction;
import com.example.mywallet.ValueItem;
import com.example.mywallet.Wallet;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WalletDao {
    @Query("SELECT * FROM wallets")
    LiveData<List<Wallet>> getAllWallets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertWallets(List<Wallet> wallets);

    @Insert
    void insertWallet(Wallet wallet);

    @Update
    void updateWallet(Wallet wallet);


    @Query("DELETE FROM wallets")
    void deleteAllWallets();

    @Delete
    void DeleteWallet(Wallet wallet);

    //Value Items
    @Query(("SELECT*FROM  valueitems"))
    LiveData<List<ValueItem>> getAllValueItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertValueItems(List<ValueItem> valueItem);

    @Insert
    void insertValueItem(ValueItem valueItem);

    @Update
    void updateValueItem(ValueItem valueItem);


    @Query("DELETE FROM valueitems")
    void deleteAllValueItems();

    @Delete
    void DeleteValueItem(ValueItem valueItem);

    //


    //Counterparties
    @Query("SELECT*FROM  Counterparties")
    LiveData<List<Counterparty>> getAllCounterparties();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertCounterparties(List<Counterparty> Counterparty);

    @Insert
    void insertCounterparty(Counterparty counterparty);

    @Update
    void updateCounterparty(Counterparty counterparty);


    @Query("DELETE FROM Counterparties")
    void deleteAllCounterparties();

    @Delete
    void DeleteCounterparty(Counterparty counterparty);

    //


    //Transactions
    @Query("SELECT*FROM  transactions Order by date")
    LiveData<List<Transaction>> getAllTransactions();


    @Query("SELECT*FROM  transactions WHERE wallet == :wallet Order by date")
    LiveData<List<Transaction>> getAllTransactionsByWallet(String wallet);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertTransactions(List<Transaction> transaction);

    @Insert
    void insertTransaction(Transaction transaction);

    @Update
    void updateTransaction(Transaction transaction);


    @Query("DELETE FROM transactions")
    void deleteAllTransactions();

    @Delete
    void DeleteTransaction(Transaction transaction);


    @Query("DELETE  FROM  transactions WHERE wallet == :wallet")
    void deleteTransactionByWallet(String wallet);


    //


    @Query("SELECT  Sum(turnoversum) FROM  transactions WHERE wallet == :wallet Group by wallet Order by date")
    Double getCurrentBalance(String wallet);


    @Query("SELECT  null as id,wallet,counterparty,date,sum,turnoversum FROM  transactions WHERE wallet == :wallet Group by wallet,counterparty Order by counterparty,date")
//@Query("SELECT  null as id,wallet,counterparty,null as date,null as sum,null as turnoversum FROM  transactions WHERE wallet == :wallet Group by wallet,counterparty Order by counterparty,date")
    List<Transaction> getDataByWallet(String wallet);


    @Query("SELECT  null as id,wallet,counterparty,date,sum,turnoversum FROM  transactions WHERE wallet == :wallet and counterparty == :counterparty Group by wallet,counterparty Order by counterparty,date")
    List<Transaction> getDataByWalletAndCounterparty(String wallet,String counterparty);

    @Query("SELECT  '' as id,wallet,type,counterparty,date,Sum(sum) as sum,Sum(turnoversum) as turnoversum FROM  transactions WHERE wallet == :wallet and type == :type and date >= :from AND date <= :to Group by wallet,counterparty,type Order by counterparty,date")
    List<Transaction> getDataByWalletAndType(String wallet, String type, Long from,Long to);

    @Query("SELECT  '' as id,date,wallet,type,Sum(sum) as sum,Sum(turnoversum) as turnoversum FROM  transactions WHERE wallet == :wallet  and date >= :from AND date <= :to Group by wallet,type ")
    List<Transaction> getDataByWalletGroupType(String wallet,  Long from,Long to);

    @Query("UPDATE transactions SET wallet = :newWallet WHERE wallet == :wallet")
    void updateTransactionsByWallet(String wallet,String newWallet);
}
