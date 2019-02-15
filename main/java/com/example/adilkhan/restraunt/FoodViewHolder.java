package com.example.adilkhan.restraunt;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adilkhan.restraunt.Interface.ItenClick;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView textfood;
    ImageView imagefood;
    ImageView favImage;

    private ItenClick itemClick;

    public void setItemClick(ItenClick itemClick) {
        this.itemClick = itemClick;
    }

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);


        imagefood = itemView.findViewById(R.id.imagefood);
        textfood = itemView.findViewById(R.id.textfood);
        favImage = itemView.findViewById(R.id.fav);

        imagefood.setScaleType(ImageView.ScaleType.FIT_XY);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        itemClick.onclick(getAdapterPosition(), v, false);

    }
}
