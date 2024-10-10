package com.example.myexpenses;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddExpense extends AppCompatActivity {
    private EditText edtname, edtdescription, edtamount;
    private Button btnsave,btnback;
    private DatabaseHelper db;
    private Spinner spinnerCategory;
    private SharedPrefManager shpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add);

        edtname = findViewById(R.id.edttxtname);
        edtdescription = findViewById(R.id.edttxtdescription);
        edtamount = findViewById(R.id.edttxtamount);
        spinnerCategory = findViewById(R.id.spinnercategories);
        btnsave = findViewById(R.id.btnexpsave);
        btnback = findViewById(R.id.btnback);
        db = new DatabaseHelper(this);
        shpm = new SharedPrefManager(this);
        String username = shpm.getUsername();

        List<String> mycategories = db.getCategories(username);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mycategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtname.getText().toString();
                String description = edtdescription.getText().toString();
                String amount = edtamount.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();
                if(!name.isEmpty() && !description.isEmpty() && !amount.isEmpty() && !category.isEmpty()){
                    if (db.addExpense(new Expense(name, description, Integer.parseInt(amount), category),username)) {
                        Toast.makeText(AddExpense.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                        edtname.setText("");
                        edtdescription.setText("");
                        edtamount.setText("");
                    }else {
                        Toast.makeText(AddExpense.this, "Failed to add expense", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddExpense.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
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
