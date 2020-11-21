package com.example.mywallet.data;

import android.content.Context;

import com.example.mywallet.Wallet;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Wallet.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase database;
    private static final String DB_NAME = "wallets.db";
    private static final Object LOCK = new Object();

    public static AppDataBase getInstance(Context context){

        synchronized (LOCK){

            if (database == null){
                database = Room.databaseBuilder(context,AppDataBase.class,DB_NAME).fallbackToDestructiveMigration().build();
            }
        }

        return database;
    }


    public abstract WalletDao walletDao();


}
