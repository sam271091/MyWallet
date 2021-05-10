package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mywallet.R;
import com.google.api.services.drive.model.File;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilesSelectorAdapter extends RecyclerView.Adapter<FilesSelectorAdapter.FilesSelectorViewHolder> {

    private ArrayList<File> files;

    public FilesSelectorAdapter() {
        files = new ArrayList<>();
    }

    @NonNull
    @Override
    public FilesSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item,parent,false);
        return new FilesSelectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesSelectorViewHolder holder, int position) {
        File file = files.get(position);
       if (file != null){
           holder.textViewFileName.setText(file.getName());

           double kbValue = 0;

           kbValue = file.getSize().doubleValue()/1024;

           holder.textViewSize.setText(String.format("%.2f",kbValue));
       }


    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class  FilesSelectorViewHolder extends RecyclerView.ViewHolder{
         TextView textViewFileName;
         TextView textViewSize;

        public FilesSelectorViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewFileName = itemView.findViewById(R.id.textViewFileName);
            textViewSize     = itemView.findViewById(R.id.textViewSize);
        }
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
}
