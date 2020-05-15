package com.example.myapplication.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName=(TextView) itemView.findViewById(R.id.menu_name);
        imageView=(ImageView) itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
