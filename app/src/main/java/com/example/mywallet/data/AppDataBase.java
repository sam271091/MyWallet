package com.example.mywallet.data;

import android.content.Context;

import com.example.mywallet.Counterparty;
import com.example.mywallet.Transaction;
import com.example.mywallet.ValueItem;
import com.example.mywallet.Wallet;
import com.example.mywallet.converters.DateConverter;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Wallet.class, Counterparty.class, ValueItem.class, Transaction.class},version = 2)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase database;
    private static final String DB_NAME = "wallets.db";
    private static final Object LOCK = new Object();
    @TypeConverters({DateConverter.class})
    public static AppDataBase getInstance(Context context){

        synchronized (LOCK){

            if (database == null){
                database = Room.databaseBuilder(context,AppDataBase.class,DB_NAME).addMigrations(MIGRATION_1_2).build();
            }
        }

        return database;
    }



    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE valueitems "
                    + " ADD COLUMN pictureID TEXT");
        }
    };

    public abstract WalletDao walletDao();


}
