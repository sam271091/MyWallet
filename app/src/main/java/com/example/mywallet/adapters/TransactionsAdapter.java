package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.Counterparty;
import com.example.mywallet.MainViewModel;
import com.example.mywallet.R;
import com.example.mywallet.Transaction;
import com.example.mywallet.Type;
import com.example.mywallet.ValueItem;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {
    List<Transaction> transactions;

    private OnTransactionClickListener onTransactionClickListener;
//    private OnWalletDeleteClickListener onTransactionDeleteClickListener;

    public TransactionsAdapter() {
        transactions = new ArrayList<>();
    }

    public interface OnTransactionClickListener{
        void OnTransactionClick(int position);
    }

//    public interface OnWalletDeleteClickListener{
//        void OnWalletDeleteClick(int position);
//    }

    public void setOnTransactionClickListener(OnTransactionClickListener onTransactionClickListener) {
        this.onTransactionClickListener = onTransactionClickListener;
    }

//    public void setOnWalletDeleteClickListener(OnWalletDeleteClickListener onWalletDeleteClickListener) {
//        this.onWalletDeleteClickListener = onWalletDeleteClickListener;
//    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_item,parent,false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionViewHolder holder, final int position) {
        Transaction transaction = transactions.get(position);

        DateFormat df = new SimpleDateFormat("dd MMMM yyyy k:m:s");
        String date = df.format(transaction.getDate());

        holder.textViewListDate.setText(date.toString());

        Counterparty counterparty = transaction.getCounterparty();

        if (counterparty != null){
            holder.textViewListCounterparty.setText(counterparty.getName());
        } else {
            holder.textViewListCounterparty.setText("-");
        }

        ValueItem valueItem = transaction.getValueItem();

        if (valueItem != null){
            holder.textViewListValueItem.setText(valueItem.getName());
        } else {
            holder.textViewListValueItem.setText("-");
        }

        holder.textViewListSum.setText(Double.toString(transaction.getSum()));


        if (transaction.getType() == Type.receipt){
            holder.imageViewListType.setImageResource(R.drawable.add);
        } else {
            holder.imageViewListType.setImageResource(R.drawable.minus);
        }


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder{
        TextView textViewListDate;
        TextView textViewListCounterparty;
        TextView textViewListValueItem;
        TextView textViewListSum;
        ImageView imageViewListType;
//        Button buttonDelete;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewListDate = itemView.findViewById(R.id.textViewListDate);
            textViewListCounterparty = itemView.findViewById(R.id.textViewListCounterparty);
            textViewListValueItem = itemView.findViewById(R.id.textViewListValueItem);
            textViewListSum = itemView.findViewById(R.id.textViewListSum);
            imageViewListType = itemView.findViewById(R.id.imageViewListType);
//            buttonDelete = itemView.findViewById(R.id.buttonDelete);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTransactionClickListener != null){
                        onTransactionClickListener.OnTransactionClick(getAdapterPosition());
                    }

                }
            });

//            buttonDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   if (onWalletDeleteClickListener != null){
//                       onWalletDeleteClickListener.OnWalletDeleteClick(getAdapterPosition());
//                   }
//                }
//            });
        }
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }


    public List<Transaction> getTransactions(){
        return transactions;
    }
}
