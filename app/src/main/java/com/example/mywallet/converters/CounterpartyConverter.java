package com.example.mywallet.converters;

import com.example.mywallet.Counterparty;
import com.google.gson.Gson;

import androidx.room.TypeConverter;

public class CounterpartyConverter {
    @TypeConverter
    public static String CounterpartyToString(Counterparty counterparty){
        return new Gson().toJson(counterparty);
    }

    @TypeConverter
    public static Counterparty StringToCounterparty(String counterpartyAsJson){
        Gson gson = new Gson();

        Counterparty counterparty = gson.fromJson(counterpartyAsJson,Counterparty.class);

        return counterparty;
    }
}
