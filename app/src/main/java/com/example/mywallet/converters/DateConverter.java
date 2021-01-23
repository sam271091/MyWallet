package com.example.mywallet.converters;



import com.example.mywallet.Counterparty;
import com.example.mywallet.Type;
import com.example.mywallet.ValueItem;
import com.example.mywallet.Wallet;
import com.google.gson.Gson;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }










}
