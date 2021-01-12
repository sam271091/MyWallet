package com.example.mywallet;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Type type;
    private Wallet wallet;
    private ValueItem valueItem;
    private Counterparty counterparty;
    private double sum;

    public Transaction(int id, Type type, Wallet wallet, ValueItem valueItem, Counterparty counterparty, double sum) {
        this.id = id;
        this.type = type;
        this.wallet = wallet;
        this.valueItem = valueItem;
        this.counterparty = counterparty;
        this.sum = sum;
    }

    @Ignore
    public Transaction(Type type, Wallet wallet, ValueItem valueItem, Counterparty counterparty, double sum) {
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

    public ValueItem getValueItem() {
        return valueItem;
    }

    public void setValueItem(ValueItem valueItem) {
        this.valueItem = valueItem;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
