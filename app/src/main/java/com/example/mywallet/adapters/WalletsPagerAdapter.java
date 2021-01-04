package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mywallet.R;
import com.example.mywallet.Wallet;
import com.example.mywallet.fragments.WalletFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

public class WalletsPagerAdapter extends FragmentStateAdapter {

    List<Wallet> wallets;
    private OnWalletClickListener onWalletClickListener;
    private OnWalletDeleteClickListener onWalletDeleteClickListener;

    public WalletsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
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

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Wallet wallet = wallets.get(position);

//        String tag = "f" + holder.getItemId();
//
//
//        Fragment fragment =   FragmentManager.findFragmentByTag(tag);
//
//        if (fragment != null) {
//            //manual update fragment
//        } else {
//            // fragment might be null, if it`s call of notifyDatasetChanged()
//            // which is updates whole list, not specific fragment
//            super.onBindViewHolder(holder, position, payloads);
//        }



    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Wallet wallet = wallets.get(position);
        return new WalletFragment(wallet,onWalletClickListener,onWalletDeleteClickListener,position);
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
        notifyDataSetChanged();
    }

    public List<Wallet> getWallets(){
        return wallets;
    }

    public void clearWallets(){
        wallets.clear();
        notifyDataSetChanged();
    }
}
