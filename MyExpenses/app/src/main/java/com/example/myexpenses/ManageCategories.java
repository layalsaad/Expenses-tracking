package com.example.myexpenses;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageCategories extends AppCompatActivity {
    private EditText edtcatname;
    private Button btnAddCategory, btnBack;
    private DatabaseHelper db;
    private Spinner spinnerCategory;
    private ArrayList<String> mycategories;
    private ArrayAdapter<String> adapter;
    private SharedPrefManager shpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_categories);

        db = new DatabaseHelper(this);
        shpm = new SharedPrefManager(this);

        edtcatname = findViewById(R.id.edttxtcatname);
        btnAddCategory = findViewById(R.id.btnaddcat);
        btnBack = findViewById(R.id.btnback);
        spinnerCategory = findViewById(R.id.spinnercats);

        //add default categories
        mycategories = new ArrayList<>();
        mycategories.add("Debt");
        mycategories.add("Products");
        mycategories.add("Marketing");
        mycategories.add("Fees");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mycategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        String username = shpm.getUsername();

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = edtcatname.getText().toString();
                if(!categoryName.isEmpty()){
                    if (db.addCategory(categoryName) && db.addUserCategory(categoryName,username)) {
                        mycategories.add(categoryName);
                        adapter.notifyDataSetChanged();
                        spinnerCategory.setAdapter(adapter);
                        edtcatname.setText("");
                        Toast.makeText(ManageCategories.this, "Category added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManageCategories.this, "Failed to add category", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        spinnerCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mycategories.remove(position);
                String category = mycategories.get(position);
                db.removecategory(category,username);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
