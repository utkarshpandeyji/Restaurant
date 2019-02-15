package com.example.adilkhan.restraunt;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adilkhan.restraunt.Database.Database;
import com.example.adilkhan.restraunt.Interface.CartAdapter;
import com.example.adilkhan.restraunt.Model.OrderModel;
import com.example.adilkhan.restraunt.Model.RequestModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    Button placeOrder;
    TextView txtTotalPrice;

    List<OrderModel> cart = new ArrayList<>();
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

       /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Name", "");*/

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        placeOrder = findViewById(R.id.btnPlaceOrder);
        txtTotalPrice = findViewById(R.id.total);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.size() > 0)
                showAlertDialoge();
                else
                {
                    Toast.makeText(MyCart.this, "Cart is Empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        loadListFood();
    }

    private void showAlertDialoge() {

        AlertDialog.Builder alertdialoge = new AlertDialog.Builder(MyCart.this);
        alertdialoge.setTitle("One More Step!");
        alertdialoge.setMessage("Enter Your Address: ");

        LayoutInflater inflater = this.getLayoutInflater();
        View order_address_comment = inflater.inflate(R.layout.order_adress_comment,null);

        final MaterialEditText edtAddress = order_address_comment.findViewById(R.id.edtAdress);
        final MaterialEditText edtComment = order_address_comment.findViewById(R.id.edtComment);



       /* final EditText editText = new EditText(MyCart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(lp);*/
        alertdialoge.setView(order_address_comment);
        alertdialoge.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertdialoge.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                RequestModel requests = new RequestModel(
                        Common.currentUser.getMobile(),
                        Common.currentUser.getUsername(),
                        edtAddress.getText().toString(),
                        "0",
                        edtComment.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                request.child(String.valueOf(System.currentTimeMillis())).setValue(requests);

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(MyCart.this, "ThankYou, Order is Placed.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        alertdialoge.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertdialoge.show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {

        cart.remove(position);

        new Database(this).cleanCart();

        for(OrderModel item:cart)
            new Database(this).addToCart(item);
        loadListFood();
    }

    private void loadListFood() {

        cart = new Database(this).getCarts();
        cartAdapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        int total = 0;
        for(OrderModel order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }
}
