package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.MainViewModel;
import com.example.mywallet.R;
import com.example.mywallet.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.WalletViewHolder> {
    List<Wallet> wallets;
    private OnWalletClickListener onWalletClickListener;
    private OnWalletDeleteClickListener onWalletDeleteClickListener;

    public WalletsAdapter() {
        wallets = new ArrayList<>();
    }

    public interface OnWalletClickListener{
        void OnWalletClick(int position);
    }

    public interface OnWalletDeleteClickListener{
        void OnWalletDeleteClick(int position);
    }

    public void setOnWalletClickListener(OnWalletClickListener onWalletClickListener) {
        this.onWalletClickListener = onWalletClickListener;
    }

    public void setOnWalletDeleteClickListener(OnWalletDeleteClickListener onWalletDeleteClickListener) {
        this.onWalletDeleteClickListener = onWalletDeleteClickListener;
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallets_item,parent,false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WalletViewHolder holder, final int position) {
        Wallet wallet = wallets.get(position);
        holder.textViewName.setText(wallet.getName());


//        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Wallet wallet =  wallets.get(position);;
//                view.getContext().
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    class WalletViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        Button buttonDelete;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewWalletName);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onWalletClickListener != null){
                        onWalletClickListener.OnWalletClick(getAdapterPosition());
                    }

                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (onWalletDeleteClickListener != null){
                       onWalletDeleteClickListener.OnWalletDeleteClick(getAdapterPosition());
                   }
                }
            });
        }
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
        notifyDataSetChanged();
    }


    public List<Wallet> getWallets(){
        return wallets;
    }
}
