package com.example.adilkhan.restraunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.adilkhan.restraunt.Database.Database;
import com.example.adilkhan.restraunt.Interface.ItenClick;
import com.example.adilkhan.restraunt.Model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {


    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase fbfood;
    DatabaseReference dbfood;

    Database localdb;

    String categoryId="";

    FirebaseRecyclerAdapter<FoodModel, FoodViewHolder> adapter;

    FirebaseRecyclerAdapter<FoodModel, FoodViewHolder> searchAdapter;

    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


    fbfood = FirebaseDatabase.getInstance();
    dbfood = fbfood.getReference("Foods");

    localdb = new Database(this);

    recycler_food = findViewById(R.id.recycler_food);
    recycler_food.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(this);
    recycler_food.setLayoutManager(layoutManager);


    if(getIntent()!=null)
    categoryId = getIntent().getStringExtra("CategoryId");
    if (!categoryId.isEmpty() && categoryId != null)
    {
        categoryId = getIntent().getStringExtra("CategoryId");

        Log.d("India",categoryId);

        if(Common.isConnectedtoInternet(getBaseContext()))
        loadlistfood(categoryId);
        else
        {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //Search

    materialSearchBar = findViewById(R.id.searchBar);
    materialSearchBar.setHint("Enter Your Food");

    loadSuggest();

    materialSearchBar.setLastSuggestions(suggestList);
    materialSearchBar.setCardViewElevation(10);
    materialSearchBar.addTextChangeListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            List<String> suggest = new ArrayList<String>();
            for (String search:suggestList)
            {
                if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                    suggest.add(search);
            }
            materialSearchBar.setLastSuggestions(suggest);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
    materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
        @Override
        public void onSearchStateChanged(boolean enabled) {
            if(!enabled)
                recycler_food.setAdapter(adapter);
        }

        @Override
        public void onSearchConfirmed(CharSequence text) {
            startSearch(text);

        }

        @Override
        public void onButtonClicked(int buttonCode) {

        }
    });

    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<FoodModel, FoodViewHolder>(
                FoodModel.class,
                R.layout.fooditem,
                FoodViewHolder.class,
                dbfood.orderByChild("name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, FoodModel model, int position) {

                viewHolder.textfood.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imagefood);

                final FoodModel local = model;
                viewHolder.setItemClick(new ItenClick() {
                    @Override
                    public void onclick(int position, View view, boolean isLongClick) {
                        Intent foodDetail = new Intent(FoodList.this, FoodDetails.class);
                        foodDetail.putExtra("FoodId",searchAdapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recycler_food.setAdapter(searchAdapter);
    }

    private void loadSuggest() {

        dbfood.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren())
                        {
                            FoodModel item = postSnapShot.getValue(FoodModel.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void loadlistfood(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<FoodModel, FoodViewHolder>(FoodModel.class, R.layout.fooditem,
                FoodViewHolder.class, dbfood.orderByChild("menuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(final FoodViewHolder viewHolder, final FoodModel model, final int position) {

                viewHolder.textfood.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imagefood);

                //Add favorites
                if(localdb.isFavorite(adapter.getRef(position).getKey()))
                    viewHolder.favImage.setImageResource(R.drawable.ic_favorite_black_24dp);

                //Click to change state of favorites

                viewHolder.favImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!localdb.isFavorite(adapter.getRef(position).getKey()))
                        {
                            localdb.addToFavorites(adapter.getRef(position).getKey());
                            viewHolder.favImage.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(FoodList.this, ""+model.getName()+" was added to favorites", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            localdb.removeFromFavorites(adapter.getRef(position).getKey());
                            viewHolder.favImage.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            Toast.makeText(FoodList.this, ""+model.getName()+" was removed from favorites", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                
                final FoodModel local = model;
                viewHolder.setItemClick(new ItenClick() {
                    @Override
                    public void onclick(int position, View view, boolean isLongClick) {
                        Intent foodDetail = new Intent(FoodList.this, FoodDetails.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });

            }
        };

        recycler_food.setAdapter(adapter);

    }
}
