package com.example.adilkhan.restraunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.adilkhan.restraunt.Database.Database;
import com.example.adilkhan.restraunt.Model.RequestModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<RequestModel, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Requests");

        recyclerView = findViewById(R.id.listordrer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null)
            loadOrders(Common.currentUser.getMobile());
        else
            loadOrders(getIntent().getStringExtra("userPhone"));
    }

    private void loadOrders(String mobile) {

        adapter = new FirebaseRecyclerAdapter<RequestModel, OrderViewHolder>(
                RequestModel.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                request.orderByChild("phone")
                .equalTo(mobile)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, RequestModel model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertcodetostatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderAddress.setText(model.getAddress());

            }

        };
        recyclerView.setAdapter(adapter);

    }


}
