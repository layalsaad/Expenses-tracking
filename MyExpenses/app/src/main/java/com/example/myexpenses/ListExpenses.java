package com.example.myexpenses;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListExpenses extends AppCompatActivity {
    private List<Expense> explist;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private Button btnBack;
    private ExpensesAdapter expadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_list);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerexpensesview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBack = findViewById(R.id.btnback);

        String username = getIntent().getStringExtra("USERNAME");
        explist = db.getExpenses(username);
        List<String> strexplist = new ArrayList<>();
        strexplist.add("Following are the expenses");
        for(Expense exp : explist){
            strexplist.add(exp.toString());
        }

        expadapter = new ExpensesAdapter(strexplist);
        recyclerView.setAdapter(expadapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
