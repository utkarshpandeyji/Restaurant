package com.example.adilkhan.restraunt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adilkhan.restraunt.Interface.ItenClick;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    TextView textView;

    private ItenClick itemClick;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView2);
        textView = itemView.findViewById(R.id.textView);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        itemView.setOnClickListener(this);

    }

    public void setItemClick(ItenClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View v) {

            itemClick.onclick(getAdapterPosition(), v, false);

    }
}
