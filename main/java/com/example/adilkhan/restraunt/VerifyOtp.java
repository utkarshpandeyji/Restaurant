package com.example.adilkhan.restraunt;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;

import java.lang.reflect.Array;
import java.util.Arrays;

public class VerifyOtp extends AppCompatActivity {

    private final int REQUEST_LOGIN=10000;
    String TAG="Adil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseAuth auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null) {
            if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {
                startActivity(new Intent(VerifyOtp.this, SignUp.class)

                        .putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                );

                finish();
            }
        }
        else
        {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setAvailableProviders(
                            Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build())).build(),REQUEST_LOGIN);


        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_LOGIN)
        {
            IdpResponse response=IdpResponse.fromResultIntent(data);

            //successfully SignIn
            if(resultCode==RESULT_OK) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {
                    startActivity(new Intent(VerifyOtp.this, SignUp.class)

                            .putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                    );
                    Toast.makeText(this," Sucessfully Verified !!!",Toast.LENGTH_SHORT).show();
                    finish();
                }


                else
                {
                    if(response==null) {
                        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(response.getErrorCode()== ErrorCodes.NO_NETWORK) {
                        Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(response.getErrorCode()== ErrorCodes.UNKNOWN_ERROR) {
                        Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //Toast.makeText(this,"Unknown Sign In Error !!!",Toast.LENGTH_SHORT).show();
            }

        }
    }
}