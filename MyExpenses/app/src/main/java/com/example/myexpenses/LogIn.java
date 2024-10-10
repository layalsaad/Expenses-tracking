package com.example.myexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    private EditText edtusername, edtpassword;
    private DatabaseHelper db;
    private Button btnlogin, btnsignup;
    private SharedPrefManager shpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        edtusername = findViewById(R.id.edttxtusername);
        edtpassword = findViewById(R.id.edttxtpassword);
        btnlogin = findViewById(R.id.btnlogin);
        btnsignup = findViewById(R.id.btnsignup);
        db = new DatabaseHelper(this);
        shpm = new SharedPrefManager(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                if (db.checkUser(username, password)) {
                    Intent i = new Intent(LogIn.this, MainActivity.class);
                    shpm.saveLoginDetails(username, password);
                    startActivity(i);
                } else {
                    Toast.makeText(LogIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, SignUp.class));
            }
        });
    }
}
