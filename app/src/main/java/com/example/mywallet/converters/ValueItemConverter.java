package com.example.mywallet.converters;

import com.example.mywallet.ValueItem;
import com.google.gson.Gson;

import androidx.room.TypeConverter;

public class ValueItemConverter {
    @TypeConverter
    public static String ValueItemToString(ValueItem valueItem){
        return new Gson().toJson(valueItem);
    }

    @TypeConverter
    public static ValueItem StringToValueItem(String valueItemAsJson){
        Gson gson = new Gson();

        ValueItem valueItem = gson.fromJson(valueItemAsJson,ValueItem.class);

        return valueItem;
    }

}
