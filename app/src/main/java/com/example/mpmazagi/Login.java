package com.example.mpmazagi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.FloatBuffer;

public class Login extends AppCompatActivity {

    TextView txtRegister;
    EditText etEmail,etPass;
    Button btnLogin;
    AppDatabase appDatabase=new AppDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegister = findViewById(R.id.txtRegister);
        etEmail= findViewById(R.id.etEmail);
        etPass= findViewById(R.id.etPass);
        btnLogin= findViewById(R.id.btnLogin);





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (appDatabase.checkPassword(etEmail.getText().toString(),etPass.getText().toString())){
                Intent goToRegistrationActivity = new Intent(MyApplication.getContext(),HomeActivity.class);
                startActivity(goToRegistrationActivity);

                }else
                {
                    Toast.makeText(Login.this, "Email Or Password Is Not Correct!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegistrationActivity = new Intent(MyApplication.getContext(),RegistrationActivity.class);
                startActivity(goToRegistrationActivity);
            }
        });
    }
}