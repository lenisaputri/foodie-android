package com.example.uasandroid.isi_menu.view_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uasandroid.Interface.ItemClickListener;
import com.example.uasandroid.R;

public class IsiRecipesCategoryDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView category_name;
    public TextView category_ingridient;
    public TextView category_method;
    public ImageView category_image;

    private ItemClickListener itemClickListener;

    public IsiRecipesCategoryDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        category_image = (ImageView)itemView.findViewById(R.id.category_image);
        category_name = (TextView)itemView.findViewById(R.id.category_name);
        category_ingridient = (TextView)itemView.findViewById(R.id.category_ingriedient);
        category_method = (TextView)itemView.findViewById(R.id.category_method);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
