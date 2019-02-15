package com.example.adilkhan.restraunt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adilkhan.restraunt.Model.UserLogin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    EditText txtUsr, txtPass;
    TextView txtForgotPwd;
    Button button2;

    ProgressDialog dialog;

    FirebaseDatabase database;
    DatabaseReference user;

    com.rey.material.widget.CheckBox ckbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);

        database = FirebaseDatabase.getInstance();

        user = database.getReference("User");


        txtUsr = findViewById(R.id.edtUsr);
        txtPass = findViewById(R.id.edtPass);
        button2 = findViewById(R.id.button2);

        txtForgotPwd = findViewById(R.id.txtForgotPwd);


        ckbRemember = findViewById(R.id.ckbRemember);

        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPwdDialog();
            }
        });

        Paper.init(this);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedtoInternet(getBaseContext())) {
                    if (ckbRemember.isChecked()) {
                        Paper.book().write(Common.USER_KEY, txtUsr.getText().toString());
                        Paper.book().write(Common.PWD_JEY, txtPass.getText().toString());

                    }
                    dialog.show();

                    final String usr = txtUsr.getText().toString();
                    final String pas = txtPass.getText().toString();

                    user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dialog.dismiss();

                            if (dataSnapshot.child(usr).exists()) {

                                UserLogin userLogin = dataSnapshot.child(usr).getValue(UserLogin.class);
                                userLogin.setMobile(txtUsr.getText().toString());
                                Log.d("utkarsh", "hello " + userLogin);

                                if (userLogin != null) {
                                    if (userLogin.getMobile().equals(usr) && userLogin.getPassword().equals(pas)) {

                                        Intent intent = new Intent(Login.this, navhome.class);
                                        Common.currentUser = userLogin;
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            } else if (!dataSnapshot.child(usr).exists()) {

                                Toast.makeText(Login.this, "User Not exist", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });


        findViewById(R.id.btnReg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reg = new Intent(Login.this, VerifyOtp.class);
                startActivity(reg);
            }
        });


    }

    private void showForgotPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forget Password");
        builder.setMessage("Enter your Secure Code");

        LayoutInflater inflater = this.getLayoutInflater();
        View forget_View = inflater.inflate(R.layout.forgot_password_layout, null);
        builder.setView(forget_View);
        builder.setIcon(R.drawable.ic_security_black_24dp);
        final MaterialEditText edtPhone = (MaterialEditText) forget_View.findViewById(R.id.edtPhone);
        final MaterialEditText edtSecureCode = (MaterialEditText) forget_View.findViewById(R.id.edtSecureCode);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserLogin userLogin = dataSnapshot.child(edtPhone.getText().toString()).getValue(UserLogin.class);
                        if (userLogin.getSecureCode().equals(edtSecureCode.getText().toString()))
                            Toast.makeText(Login.this, "Your password is: " + userLogin.getPassword(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Login.this, "Wrong Secure Code!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}


