package com.example.adilkhan.restraunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adilkhan.restraunt.Interface.ItenClick;
import com.example.adilkhan.restraunt.Model.Category_Model;
import com.example.adilkhan.restraunt.Service.ListenOrder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class navhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseRecyclerAdapter<Category_Model, MenuViewHolder> fbra;
    FirebaseDatabase db;
    DatabaseReference drfood;
    RecyclerView recycleNav;
    ShimmerLayout shimmerNav;

    TextView txtFullName;

    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navhome);

        shimmerNav = findViewById(R.id.shimmerNav);
        recycleNav = findViewById(R.id.recycleNav);

        RecyclerView.LayoutManager man=new LinearLayoutManager(this);
        recycleNav.setLayoutManager(man);
        db=FirebaseDatabase.getInstance();
        drfood= db.getReference("Category");
        shimmerNav.startShimmerAnimation();

        Toast.makeText(this, ""+Common.currentUser.getUsername(), Toast.LENGTH_SHORT).show();

        Paper.init(this);
        if(Common.isConnectedtoInternet(this))
        loadlistFood();
        else
        {
            Toast.makeText(navhome.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent service = new Intent(navhome.this, ListenOrder.class);
        startService(service);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(navhome.this, MyCart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View viewheader = navigationView.getHeaderView(0);
        txtFullName=viewheader.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getUsername());

        recycleNav.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleNav.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navhome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.refresh)
            loadlistFood();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {


        } else if (id == R.id.nav_cart) {
            Intent intentcart = new Intent(navhome.this,MyCart.class);
            startActivity(intentcart);

        } else if (id == R.id.nav_orders) {

            Intent intentorders = new Intent(navhome.this, OrderStatus.class);
            startActivity(intentorders);
        } else if (id == R.id.nav_logout) {

            Paper.book().destroy();
            Intent signIn = new Intent(navhome.this, Login.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadlistFood()
    {

        fbra = new FirebaseRecyclerAdapter<Category_Model, MenuViewHolder>(Category_Model.class, R.layout.food, MenuViewHolder.class, drfood) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category_Model model, int position) {


                Picasso.with(navhome.this).load(model.getImage()).
                        placeholder(R.drawable.ic_launcher_background).into(viewHolder.imageView);
                viewHolder.textView.setText(model.getName());


                shimmerNav.stopShimmerAnimation();
                shimmerNav.setVisibility(View.GONE);
                viewHolder.textView.setText(model.getName());


                final Category_Model clickItem = model;
                viewHolder.setItemClick(new ItenClick() {
                    @Override
                    public void onclick(int position, View view, boolean isLongClick) {

                        Intent foodlist= new Intent(navhome.this, FoodList.class);
                        foodlist.putExtra("CategoryId" , fbra.getRef(position).getKey());
                        startActivity(foodlist);

                    }
                });
            }
        };
        recycleNav.setAdapter(fbra);

    }
}
