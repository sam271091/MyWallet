package com.example.mywallet;

import com.example.mywallet.converters.UUIDConverter;

import java.io.Serializable;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "wallets")
public class Wallet implements Serializable {
    @PrimaryKey
    @TypeConverters(value = UUIDConverter.class)
    @NonNull
    private UUID id;
    private String name;



    public Wallet() {
        this.id = UUID.randomUUID();
    }

    @Ignore
    public Wallet(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    @Ignore
    public Wallet(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
