package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mywallet.Counterparty;
import com.example.mywallet.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CounterpartiesAdapter extends RecyclerView.Adapter<CounterpartiesAdapter.CounterpartyViewHolder> {
    List<Counterparty> counterparties ;
    private OnCounterpartyClickListener onCounterpartyClickListener;

    public CounterpartiesAdapter() {
        counterparties = new ArrayList<>();
    }

    public interface OnCounterpartyClickListener{
        void onCounterpartyClick(int position);
    }

    public void setOnCounterpartyClickListener(OnCounterpartyClickListener onCounterpartyClickListener) {
        this.onCounterpartyClickListener = onCounterpartyClickListener;
    }

    @NonNull
    @Override
    public CounterpartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.counterparties_item,parent,false);
        return new CounterpartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CounterpartyViewHolder holder, int position) {
        Counterparty counterparty = counterparties.get(position);
        holder.textViewItemCounterParty.setText(counterparty.getName());
        holder.textViewCounterpartyItemComment.setText(counterparty.getComment());
    }

    @Override
    public int getItemCount() {
        return counterparties.size();
    }

    class CounterpartyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewItemCounterParty;
        TextView textViewCounterpartyItemComment;

        public CounterpartyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewItemCounterParty             = itemView.findViewById(R.id.textViewItemCounterParty);
            textViewCounterpartyItemComment      = itemView.findViewById(R.id.textViewCounterpartyItemComment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCounterpartyClickListener != null){
                        onCounterpartyClickListener.onCounterpartyClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public List<Counterparty> getCounterparties() {
        return counterparties;
    }

    public void setCounterparties(List<Counterparty> counterparties) {
        this.counterparties = counterparties;
        notifyDataSetChanged();
    }
}
