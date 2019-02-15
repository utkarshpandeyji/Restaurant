package com.example.adilkhan.restraunt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.adilkhan.restraunt.Interface.ItenClick;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderAddress, txtOrderStatus, txtOrderPhone;

    private ItenClick itenClick;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderAddress = itemView.findViewById(R.id.order_adress);
        txtOrderStatus = itemView.findViewById(R.id.order_status);

        itemView.setOnClickListener(this);
    }

    public void setItenClick(ItenClick itenClick) {
        this.itenClick = itenClick;
    }

    @Override
    public void onClick(View v) {

        itenClick.onclick(getAdapterPosition(),v,false);
    }
}
