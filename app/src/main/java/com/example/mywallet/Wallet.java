package com.example.mywallet;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "wallets")
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;


    public Wallet(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Ignore
    public Wallet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
