package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mywallet.R;
import com.example.mywallet.ValueItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ValueItemsAdapter extends RecyclerView.Adapter<ValueItemsAdapter.ValueItemViewHolder> {


    List<ValueItem> valueItems;
    private OnValueItemClickListener onValueItemClickListener;


    public ValueItemsAdapter() {
        valueItems = new ArrayList<>();
    }

    public interface OnValueItemClickListener{
        void OnValueItemClick(int position);
        void OnLongClick(int position);
    }

    public void setOnValueItemClickListener(OnValueItemClickListener onValueItemClickListener) {
        this.onValueItemClickListener = onValueItemClickListener;
    }

    @NonNull
    @Override
    public ValueItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.value_items_item,parent,false);
        return new ValueItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ValueItemViewHolder holder, int position) {

        ValueItem valueItem = valueItems.get(position);
        holder.textViewValueItemName.setText(valueItem.getName());

    }

    @Override
    public int getItemCount() {
        return valueItems.size();
    }

    class ValueItemViewHolder extends RecyclerView.ViewHolder{
       TextView textViewValueItemName;
       public ValueItemViewHolder(@NonNull View itemView) {
           super(itemView);
           textViewValueItemName = itemView.findViewById(R.id.textViewListDate);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (onValueItemClickListener != null){
                       onValueItemClickListener.OnValueItemClick(getAdapterPosition());
                   }
               }
           });

           itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View view) {
                   if (onValueItemClickListener != null){
                       onValueItemClickListener.OnLongClick(getAdapterPosition());
                   }
                   return true;
               }
           });
       }
   }


    public List<ValueItem> getValueItems() {
        return valueItems;
    }

    public void setValueItems(List<ValueItem> valueItems) {
        this.valueItems = valueItems;
        notifyDataSetChanged();
    }



}
