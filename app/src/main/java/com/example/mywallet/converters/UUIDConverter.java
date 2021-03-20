package com.example.mywallet.converters;

import java.util.UUID;

import androidx.room.TypeConverter;

public class UUIDConverter {

    @TypeConverter
    public static String fromUUID(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static UUID uuidFromString(String string) {
        if (!string.isEmpty()){
            return UUID.fromString(string);
        }

        return UUID.fromString("00000000-0000-0000-0000-000000000000");


    }
}
