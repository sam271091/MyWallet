package com.example.mywallet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywallet.MainActivity;
import com.example.mywallet.R;
import com.example.mywallet.Wallet;
import com.example.mywallet.adapters.WalletsPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WalletFragment extends Fragment {
    private Wallet wallet;
    private int adapterPosition;
    private WalletsPagerAdapter.OnWalletClickListener onWalletClickListener;
    private WalletsPagerAdapter.OnWalletDeleteClickListener onWalletDeleteClickListener;

    public WalletFragment(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(
                R.layout.wallets_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

                TextView itemView =  view.findViewById(R.id.textViewWalletName);
                itemView.setText(wallet.getName());
                Button buttonDelete = view.findViewById(R.id.buttonDelete);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onWalletClickListener != null){
                    onWalletClickListener.OnWalletClick(adapterPosition);
                }

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onWalletDeleteClickListener != null){
                    onWalletDeleteClickListener.OnWalletDeleteClick(adapterPosition);
                }
            }
        });


    }

}
