package com.example.adilkhan.restraunt;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.adilkhan.restraunt.Database.Database;
import com.example.adilkhan.restraunt.Model.FoodModel;
import com.example.adilkhan.restraunt.Model.OrderModel;
import com.example.adilkhan.restraunt.Model.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class FoodDetails extends AppCompatActivity implements RatingDialogListener {

    TextView food_description, foodName, food_price;
    ImageView imgfoodetail;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart, btnRating;
    RatingBar ratingBar;
    ElegantNumberButton numberButton;

    String foodId="";
    //SharedPreferences sharedPreferences;
    FirebaseDatabase database;
    DatabaseReference foods;
    DatabaseReference ratingTbl;
    FoodModel foodModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");
        ratingTbl = database.getReference("Rating");
        numberButton = findViewById(R.id.number_button);
        btncart = findViewById(R.id.btnCart);
       // sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  SharedPreferences.Editor editor = sharedPreferences.edit();

                /*editor.putString("Name", foodModel.getName());
                editor.putString("Id", foodId);
                editor.putString("Price", foodModel.getPrice());
                editor.putString("Discount",foodModel.getDiscount());
                editor.commit();*/
                               new Database(getBaseContext()).addToCart(new OrderModel(
                        foodId,
                        foodModel.getName(),
                        numberButton.getNumber(),
                        foodModel.getPrice(),
                        foodModel.getDiscount()

                ));

                Toast.makeText(FoodDetails.this, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });
/*btncart.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        String name = sharedPreferences.getString("Name", "");
        Toast.makeText(FoodDetails.this, "this"+name, Toast.LENGTH_SHORT).show();
        return false;
    }
});*/
        food_description = findViewById(R.id.food_description);
        foodName = findViewById(R.id.foodName);
        food_price = findViewById(R.id.food_description);

        imgfoodetail = findViewById(R.id.imgfoodetail);

        btnRating = findViewById(R.id.btnRating);
        ratingBar = findViewById(R.id.ratingBar);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if(getIntent()!= null)
            foodId = getIntent().getStringExtra("FoodId");

        if(!foodId.isEmpty())
        {
            if(Common.isConnectedtoInternet(getBaseContext()))
            {
                getDetailFood(foodId);
                getRatingFood(foodId);
            }

            else
            {
                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

    }

    private void getRatingFood(String foodId) {

        Query foodRating = ratingTbl.orderByChild("FoodId").equalTo(foodId);

        foodRating.addValueEventListener(new ValueEventListener() {
            int sum = 0, count = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+= Integer.parseInt(item.getRateValue());
                    count++;
                }
                if(count!=0)
                {
                    float average = sum/count;
                    ratingBar.setRating(average);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {

        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very bad", "Not Good", "Quite Ok", "Very Good", "Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate this Food")
                .setDescription("Please choose any star and give your FeedBack")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.black)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(FoodDetails.this)
                .show();

    }

    private void getDetailFood(String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foodModel = dataSnapshot.getValue(FoodModel.class);

                Picasso.with(getBaseContext()).load(foodModel.getImage()).into(imgfoodetail);

                collapsingToolbarLayout.setTitle(foodModel.getName());

                food_price.setText(foodModel.getPrice());
                foodName.setText(foodModel.getName());
                food_description.setText(foodModel.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {

        final Rating rating = new Rating(Common.currentUser.getMobile(),foodId,String.valueOf(value),comments);

        ratingTbl.child(Common.currentUser.getMobile()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(Common.currentUser.getMobile()).exists())
                {
                    ratingTbl.child(Common.currentUser.getMobile()).removeValue();
                    ratingTbl.child(Common.currentUser.getMobile()).setValue(rating);
                }
                else
                {
                    ratingTbl.child(Common.currentUser.getMobile()).setValue(rating);
                }
                Toast.makeText(FoodDetails.this, "Thankyou for your feedback!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
