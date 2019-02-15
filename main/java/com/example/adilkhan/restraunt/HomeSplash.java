package com.example.adilkhan.restraunt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.adilkhan.restraunt.Model.UserLogin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class HomeSplash extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_splash);

        /*sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        firstTime = sharedPreferences.getBoolean("firstTime", true);

        if (firstTime) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    firstTime = false;
                    editor.putBoolean("firstTime", firstTime);
                    editor.apply();

                    Intent intent = new Intent(HomeSplash.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }, 4000);

        } else {
            Intent intent = new Intent(HomeSplash.this, Login.class);
            startActivity(intent);
            finish();
        }*/

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(HomeSplash.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 4000);



       /* else
        {
            Paper.init(this);

            String user = Paper.book().read(Common.USER_KEY);
            String pwd = Paper.book().read(Common.PWD_JEY);

            if(user != null && pwd != null)
            {
                if(!user.isEmpty() && !pwd.isEmpty())
                    login(user,pwd);
            }
            finish();
        }

    }*/
    }

    private void login(final String usr, final String pas) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user = database.getReference("User");

        if(Common.isConnectedtoInternet(getBaseContext())) {

            user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(usr).exists()) {

                        UserLogin userLogin = dataSnapshot.child(usr).getValue(UserLogin.class);
                        userLogin.setMobile(usr);
                        Log.d("utkarsh", "hello " + userLogin);

                        if (userLogin != null) {
                            if (userLogin.getMobile().equals(usr) && userLogin.getPassword().equals(pas)) {

                                Intent intent = new Intent(HomeSplash.this, navhome.class);
                                Common.currentUser = userLogin;
                                startActivity(intent);
                                finish();

                            }
                        }
                    } else if (!dataSnapshot.child(usr).exists()) {

                        Toast.makeText(HomeSplash.this, "User Not exist", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
    }


