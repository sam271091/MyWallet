package com.example.mywallet;

import com.example.mywallet.converters.CounterpartyConverter;
import com.example.mywallet.converters.DateConverter;
import com.example.mywallet.converters.TypeConverter;
import com.example.mywallet.converters.UUIDConverter;
import com.example.mywallet.converters.ValueItemConverter;
import com.example.mywallet.converters.WalletConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "transactions")

public class Transaction implements Serializable {
    @PrimaryKey
    @TypeConverters(value = UUIDConverter.class)
    @NonNull
    private UUID id;
    @TypeConverters(value = DateConverter.class)
    private Date date;
    @TypeConverters(value = TypeConverter.class)
    private Type type;
    @TypeConverters(value = WalletConverter.class)
    private Wallet wallet;
    @TypeConverters(value = ValueItemConverter.class)
    private ValueItem valueItem;
    @TypeConverters(value = CounterpartyConverter.class)
    private Counterparty counterparty;
    private double sum;
    private double turnoversum;
    private String comment;


    public Transaction() {
        this.id = UUID.randomUUID();
    }

    public double getTurnoversum() {
        return turnoversum;
    }

    public void setTurnoversum(double turnoversum) {
        this.turnoversum = turnoversum;
    }

    @Ignore
    public Transaction(UUID id, Date date, Type type, Wallet wallet, ValueItem valueItem, Counterparty counterparty, double sum, double turnoversum, String comment) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.wallet = wallet;
        this.valueItem = valueItem;
        this.counterparty = counterparty;
        this.sum = sum;
        this.turnoversum = turnoversum;
        this.comment = comment;
    }

    @Ignore
    public Transaction(Type type,Date date, Wallet wallet, ValueItem valueItem, Counterparty counterparty, double sum, double turnoversum, String comment) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.type = type;
        this.wallet = wallet;
        this.valueItem = valueItem;
        this.counterparty = counterparty;
        this.sum = sum;
        this.turnoversum = turnoversum;
        this.comment = comment;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
