package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.R;
import com.example.mywallet.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.WalletViewHolder> {
    List<Wallet> wallets;

    public WalletsAdapter() {
        wallets = new ArrayList<>();
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallets_item,parent,false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        Wallet wallet = wallets.get(position);
        holder.textViewName.setText(wallet.getName());
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    class WalletViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewWalletName);
        }
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
        notifyDataSetChanged();
    }
}
