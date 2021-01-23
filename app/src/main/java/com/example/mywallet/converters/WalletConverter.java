package com.example.mywallet.converters;

import com.example.mywallet.Wallet;
import com.google.gson.Gson;

import androidx.room.TypeConverter;

public class WalletConverter {

    @TypeConverter
    public static String WalletToString(Wallet wallet){
        return new Gson().toJson(wallet);
    }

    @TypeConverter
    public static Wallet StringToWallet(String WalletAsJson){
        Gson gson = new Gson();

        Wallet wallet = gson.fromJson(WalletAsJson,Wallet.class);

        return wallet;
    }
}
