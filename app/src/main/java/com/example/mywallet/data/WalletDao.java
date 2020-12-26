package com.example.mywallet.data;

import com.example.mywallet.Wallet;

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


}
