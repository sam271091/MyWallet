package com.example.mywallet;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Type type;
    private Wallet wallet;
    private ValueItems valueItem;
    private Counterparty counterparty;
    private int sum;

    public Transaction(int id, Type type, Wallet wallet, ValueItems valueItem, Counterparty counterparty, int sum) {
        this.id = id;
        this.type = type;
        this.wallet = wallet;
        this.valueItem = valueItem;
        this.counterparty = counterparty;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public ValueItems getValueItem() {
        return valueItem;
    }

    public void setValueItem(ValueItems valueItem) {
        this.valueItem = valueItem;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
