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
   List<Wallet> getWallets();


    @Query("SELECT * FROM wallets")
    LiveData<List<Wallet>> getAllWallets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertWallets(List<Wallet> wallets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWallet(Wallet wallet);

    @Update
    void updateWallet(Wallet wallet);


    @Query("DELETE FROM wallets")
    void deleteAllWallets();

    @Delete
    void DeleteWallet(Wallet wallet);

    @Query("SELECT * FROM wallets where id == :id")
    Wallet getWalletbyId(String id);

    //Value Items
    @Query(("SELECT*FROM  valueitems"))
    LiveData<List<ValueItem>> getValueItems();


    @Query(("SELECT*FROM  valueitems"))
    List<ValueItem> getAllValueItems ();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertValueItems(List<ValueItem> valueItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    LiveData<List<Counterparty>> getCounterparties();

    @Query("SELECT*FROM  Counterparties")
    List<Counterparty> getAllCounterparties();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertCounterparties(List<Counterparty> Counterparty);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    LiveData<List<Transaction>> getTransactions();

    @Query("SELECT*FROM  transactions Order by date")
    List<Transaction> getAllTransactions();


    @Query("SELECT*FROM  transactions WHERE wallet == :wallet Order by date")
    LiveData<List<Transaction>> getAllTransactionsByWallet(String wallet);

    @Query("SELECT*FROM  transactions WHERE wallet == :wallet and date >= :from AND date <= :to Order by date")
    LiveData<List<Transaction>> getAllTransactionsByWalletForThePeriod(String wallet, Long from,Long to);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertTransactions(List<Transaction> transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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


    @Query("SELECT  Sum(sum)  FROM  transactions WHERE wallet == :wallet and type == :type Group by wallet Order by date")
    Double getTotalByType(String wallet,String type);


    @Query("SELECT  null as id,wallet,counterparty,date,sum,turnoversum FROM  transactions WHERE wallet == :wallet Group by wallet,counterparty Order by counterparty,date")
//@Query("SELECT  null as id,wallet,counterparty,null as date,null as sum,null as turnoversum FROM  transactions WHERE wallet == :wallet Group by wallet,counterparty Order by counterparty,date")
    List<Transaction> getDataByWallet(String wallet);


    @Query("SELECT  null as id,wallet,counterparty,date,sum,turnoversum FROM  transactions WHERE wallet == :wallet and counterparty == :counterparty Group by wallet,counterparty Order by counterparty,date")
    List<Transaction> getDataByWalletAndCounterparty(String wallet,String counterparty);

    @Query("SELECT  '' as id,wallet,type,counterparty,date,Sum(sum) as sum,Sum(turnoversum) as turnoversum FROM  transactions WHERE wallet == :wallet and type == :type and date >= :from AND date <= :to Group by wallet,counterparty,type Order by counterparty,date")
    List<Transaction> getDataByWalletAndType(String wallet, String type, Long from,Long to);

    @Query("SELECT  '' as id,date,wallet,type,Sum(sum) as sum,Sum(turnoversum) as turnoversum FROM  transactions WHERE wallet == :wallet  and date >= :from AND date <= :to Group by wallet,type ")
    List<Transaction> getDataByWalletGroupType(String wallet,  Long from,Long to);

    @Query("SELECT  '' as id,wallet,type,valueItem,date,Sum(sum) as sum,Sum(turnoversum) as turnoversum FROM  transactions WHERE wallet == :wallet and type == :type and date >= :from AND date <= :to Group by wallet,valueItem,type Order by valueItem,date")
    List<Transaction> getDataByWalletAndType_ValueItems(String wallet, String type, Long from,Long to);



    @Query("UPDATE transactions SET wallet = :newWallet WHERE wallet == :wallet")
    void updateTransactionsByWallet(String wallet,String newWallet);

    @Query("UPDATE transactions SET valueItem = :newValueItem WHERE valueItem == :valueItem")
    void updateTransactionsByValueItem(String valueItem,String newValueItem);

    @Query("UPDATE transactions SET counterparty = :newCounterparty WHERE counterparty == :counterparty")
    void updateTransactionsByCounterparty(String counterparty,String newCounterparty);


 @Query("SELECT*FROM  transactions WHERE wallet == :wallet and valueItem == :valueItem and type == :type and date >= :from AND date <= :to Order by date")
 List<Transaction> getAllTransactionsByWalletAndValueItemAndTypeForThePeriod(String wallet,String valueItem, String type,Long from,Long to);
}
