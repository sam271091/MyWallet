package com.example.mywallet.data;

import com.example.mywallet.Wallet;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WalletDao {
    @Query("SELECT * FROM wallets")
    LiveData<List<Wallet>> getAllWallets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertWallets(List<Wallet> wallets);

    @Insert
    void insertWallet(Wallet wallet);


    @Query("DELETE FROM wallets")
    void deleteAllWallets();



}
