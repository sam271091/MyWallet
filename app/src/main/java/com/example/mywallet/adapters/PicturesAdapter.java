package com.example.mywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mywallet.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {
    private List<String> pictures;
    private OnPictureClickListener onPictureClickListener;

    public PicturesAdapter(List<String> pictures) {
        this.pictures = pictures;
    }

    public interface OnPictureClickListener{
        void OnPicturelick(int position);

    }

    public void setOnPictureClickListener(OnPictureClickListener onPictureClickListener) {
        this.onPictureClickListener = onPictureClickListener;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PictureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        String picture = pictures.get(position);

        int id = holder.pictureImage.getResources().getIdentifier(picture,null,null);

        holder.pictureImage.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder{

        private ImageView pictureImage;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);



            pictureImage = itemView.findViewById(R.id.imageViewPicture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPictureClickListener != null){
                        onPictureClickListener.OnPicturelick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
