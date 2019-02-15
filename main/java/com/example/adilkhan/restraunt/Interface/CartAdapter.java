package com.example.adilkhan.restraunt.Interface;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.adilkhan.restraunt.Common;
import com.example.adilkhan.restraunt.Model.OrderModel;
import com.example.adilkhan.restraunt.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

    public TextView cart_item_name, cart_item_price;
    public ImageView cart_item_image;

    private ItenClick itenClick;

    public void setCart_item_name(TextView cart_item_name) {
        this.cart_item_name = cart_item_name;
    }

    public CartViewHolder(View view) {
        super(view);
        cart_item_name = view.findViewById(R.id.cart_item_name);
        cart_item_price = view.findViewById(R.id.cart_item_price);
        cart_item_image = view.findViewById(R.id.cart_item_image);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
        menu.add(0,0,getAdapterPosition(),Common.DELETE);
    }


}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<OrderModel> listdata = new ArrayList<>();
    private Context context;

    public CartAdapter(List<OrderModel> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);;
        View view = inflater.inflate(R.layout.cart_layout, viewGroup, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {

        TextDrawable drawable = TextDrawable.builder().buildRound(""+listdata.get(i).getQuantity(), Color.RED);
        cartViewHolder.cart_item_image.setImageDrawable(drawable);

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listdata.get(i).getPrice()))*(Integer.parseInt(listdata.get(i).getQuantity()));
        cartViewHolder.cart_item_price.setText(fmt.format(price));

        cartViewHolder.cart_item_name.setText(listdata.get(i).getProductName());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}
