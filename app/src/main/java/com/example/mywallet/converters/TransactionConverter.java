package com.example.mywallet.converters;

import com.example.mywallet.Counterparty;
import com.example.mywallet.Transaction;
import com.example.mywallet.Type;
import com.example.mywallet.ValueItem;
import com.example.mywallet.Wallet;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import androidx.room.TypeConverter;

public class TransactionConverter {

    @TypeConverter
    public static String TransactionToString(Transaction transaction){
        return new Gson().toJson(transaction);
    }

    @TypeConverter
    public static Transaction StringToTransaction(String TransactionAsJson){
        Gson gson = new Gson();

        try {
            JSONObject jsonObject = new JSONObject(TransactionAsJson);
            UUID id  = UUIDConverter.uuidFromString(jsonObject.get("id").toString());
            Date date = DateConverter.fromTimestamp((Long) jsonObject.get("date"));
            Type type = com.example.mywallet.converters.TypeConverter.StringToType(jsonObject.get("type").toString());

            Wallet wallet = WalletConverter.StringToWallet(jsonObject.get("wallet").toString());

            ValueItem valueItem = null;
            if (jsonObject.has("valueItem")){
                valueItem = ValueItemConverter.StringToValueItem(jsonObject.get("valueItem").toString());
            }

            Counterparty counterparty  = null;
            if (jsonObject.has("counterparty")){
                counterparty = CounterpartyConverter.StringToCounterparty(jsonObject.get("counterparty").toString());
            }



            Double sum          = Double.valueOf(jsonObject.get("sum").toString());
            Double turnoversum  = Double.valueOf(jsonObject.get("turnoversum").toString());


            String comment      = "";

            if (jsonObject.has("comment")){
                comment      = jsonObject.get("comment").toString();
            }

            Transaction transaction = new Transaction(id,date,type,wallet,valueItem,counterparty,sum,turnoversum,comment);

            return  transaction;

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Transaction transaction = gson.fromJson(TransactionAsJson, Transaction.class);

        return  null;
    }

    public static ArrayList<HashMap<String,Object>> transactionsToArrayList(List<Transaction> transactions){

        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();

        for (Transaction transaction : transactions){

            HashMap<String,Object> map = new HashMap<>();

            map.put("id",transaction.getId());
            map.put("date",DateConverter.dateToTimestamp(transaction.getDate()));
            map.put("type", transaction.getType());
            map.put("wallet",transaction.getWallet());


            map.put("valueItem", transaction.getValueItem());
            map.put("counterparty", transaction.getCounterparty());
            map.put("sum", (Double) transaction.getSum());
            map.put("turnoversum", (Double) transaction.getTurnoversum());
            map.put("comment", transaction.getComment());

            arrayList.add(map);
        }

        return  arrayList;


    }

}
