package com.example.myexpenses;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private EditText edtusername, edtpassword, edtrepeatpassword;
    private Button btnsignup,btnback;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        edtusername = findViewById(R.id.edttxtusername);
        edtpassword = findViewById(R.id.edttxtpassword);
        edtrepeatpassword = findViewById(R.id.edttxtconfirmpassword);
        btnsignup = findViewById(R.id.btnsignup);
        btnback = findViewById(R.id.btnback);
        db = new DatabaseHelper(this);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                String repeatPassword = edtrepeatpassword.getText().toString();
                if(!username.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()){
                    if (password.equals(repeatPassword)) {
                        User u = new User(username, password);
                        if (db.addUser(u)) {
                            Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            db.addDefaultCategories(u);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, "Please fill all input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
