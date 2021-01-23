package com.example.mywallet.converters;

import com.example.mywallet.Type;
import com.google.gson.Gson;


public class TypeConverter {
    @androidx.room.TypeConverter
    public static String TypeToString(Type type){
        return new Gson().toJson(type);
    }

    @androidx.room.TypeConverter
    public static Type StringToType(String typeAsJson){
        Gson gson = new Gson();

        Type type = gson.fromJson(typeAsJson,Type.class);

        return type;
    }
}

