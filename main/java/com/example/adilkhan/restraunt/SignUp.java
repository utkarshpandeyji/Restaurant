package com.example.adilkhan.restraunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adilkhan.restraunt.Model.UserLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    EditText  edtEmail, edtPass;
    MaterialEditText edtsecureCode;
    Button btnSignUp;

    FirebaseDatabase db;

    DatabaseReference dbsu;

    FirebaseAuth auth;
    String aas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        db = FirebaseDatabase.getInstance();
        dbsu = db.getReference("User");

        if(getIntent()!=null)
        {
           aas = getIntent().getStringExtra("phone");
            Toast.makeText(this, ""+aas, Toast.LENGTH_SHORT).show();

        }

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtsecureCode = findViewById(R.id.secureCode);

        btnSignUp = findViewById(R.id.btnSignup);




       // auth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = edtEmail.getText().toString();
                final String pass = edtPass.getText().toString();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(SignUp.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {

                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.length() < 6) {

                    Toast.makeText(SignUp.this, "Password too short, Enter maximum 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }





         /*       auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignUp.this, HomeActivity.class));
                            finish();
                        }

                    }
                });



            }*/



         else
                {
                    dbsu.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            if (dataSnapshot.child(aas).exists()) {
                                Toast.makeText(SignUp.this, "already register", Toast.LENGTH_SHORT).show();
                            } else {
                                UserLogin login = new UserLogin();
                                login.setMobile(aas);
                                login.setPassword(edtPass.getText().toString());
                                login.setUsername(edtEmail.getText().toString());
                                login.setSecureCode(edtsecureCode.getText().toString());

                                Common.currentUser = login;

                                dbsu.child(aas).setValue(login);
                                startActivity(new Intent(SignUp.this, navhome.class));


                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
        }

            });

    }
}
