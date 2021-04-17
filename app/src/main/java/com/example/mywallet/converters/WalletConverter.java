package com.example.mywallet.converters;

import android.app.Application;
import android.content.Context;

import com.example.mywallet.MainActivity;
import com.example.mywallet.MainViewModel;
import com.example.mywallet.Wallet;
import com.google.gson.Gson;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.TypeConverter;

public class WalletConverter {

   

    @TypeConverter
    public static String WalletToString(Wallet wallet){
        return new Gson().toJson(wallet);
    }

    @TypeConverter
    public static Wallet StringToWallet(String WalletAsJson){

//        MainActivity activity = null;
//
//        MainViewModel viewModel = new ViewModelProvider(activity,ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(MainViewModel.class);


//        Wallet wallet = this.viewModel.getWalletbyId(WalletAsJson);

        Gson gson = new Gson();

        Wallet wallet = gson.fromJson(WalletAsJson,Wallet.class);

        return wallet;
    }
}
