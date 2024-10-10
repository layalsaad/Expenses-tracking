package com.example.myexpenses;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageBudget extends AppCompatActivity {
    private EditText edtcat1, edtcat2, edtcat3, edtcat4, edtcat5;
    private EditText edtbdg1, edtbdg2, edtbdg3, edtbdg4, edtbdg5;
    private Button btnSave, btnBack;
    private DatabaseHelper db;
    private SharedPrefManager shpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_budget);

        db = new DatabaseHelper(this);
        shpm = new SharedPrefManager(this);

        edtcat1 = findViewById(R.id.edttxtcat1);
        edtcat2 = findViewById(R.id.edttxtcat2);
        edtcat3 = findViewById(R.id.edttxtcat3);
        edtcat4 = findViewById(R.id.edttxtcat4);
        edtcat5 = findViewById(R.id.edttxtcat5);
        edtbdg1 = findViewById(R.id.edttxtbdgcat1);
        edtbdg2 = findViewById(R.id.edttxtbdgcat2);
        edtbdg3 = findViewById(R.id.edttxtbdgcat3);
        edtbdg4 = findViewById(R.id.edttxtbdgcat4);
        edtbdg5 = findViewById(R.id.edttxtbdgcat5);
        btnSave = findViewById(R.id.btnexpsave);
        btnBack = findViewById(R.id.btnback);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBudget();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveBudget() {
        String category1 = edtcat1.getText().toString();
        String category2 = edtcat2.getText().toString();
        String category3 = edtcat3.getText().toString();
        String category4 = edtcat4.getText().toString();
        String category5 = edtcat5.getText().toString();
        String bdg1,bdg2,bdg3,bdg4,bdg5;
        int percentage1,percentage2,percentage3,percentage4,percentage5;
        bdg1 = edtbdg1.getText().toString();
        if(!bdg1.isEmpty()){
            percentage1 = Integer.parseInt(bdg1);
        }else{
            percentage1 = 0;
        }
        bdg2 = edtbdg2.getText().toString();
        if(!bdg2.isEmpty()){
            percentage2 = Integer.parseInt(bdg1);
        }else{
            percentage2 = 0;
        }
        bdg3 = edtbdg1.getText().toString();
        if(!bdg3.isEmpty()){
            percentage3 = Integer.parseInt(bdg1);
        }else{
            percentage3 = 0;
        }
        bdg4 = edtbdg4.getText().toString();
        if(!bdg4.isEmpty()){
            percentage4 = Integer.parseInt(bdg1);
        }else{
            percentage4 = 0;
        }
        bdg5= edtbdg5.getText().toString();
        if(!bdg5.isEmpty()){
            percentage5 = Integer.parseInt(bdg1);
        }else{
            percentage5 = 0;
        }

        boolean success = true;
        boolean budgetCheck = true;
        String username = shpm.getUsername();

        if (!category1.isEmpty() && percentage1 != 0) {
            budgetCheck = checkBudget(category1, percentage1);
            success &= db.setBudget(category1, percentage1,username);
        }
        if (!category2.isEmpty() && percentage2 != 0) {
            budgetCheck = checkBudget(category2, percentage2);
            success &= db.setBudget(category2, percentage2,username);
        }
        if (!category3.isEmpty() && percentage3 != 0) {
            budgetCheck = checkBudget(category3, percentage3);
            success &= db.setBudget(category3, percentage3,username);
        }
        if (!category4.isEmpty() && percentage4 != 0) {
            budgetCheck = checkBudget(category4, percentage4);
            success &= db.setBudget(category4, percentage4,username);
        }
        if (!category5.isEmpty() && percentage5 != 0) {
            budgetCheck = checkBudget(category5, percentage5);
            success &= db.setBudget(category5, percentage5,username);
        }

        if (success && budgetCheck) {
            Toast.makeText(this, "Budget saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save budget", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkBudget(String category, int percentage){
        List<Expense> expensesList;
        expensesList = db.getExpenses(shpm.getUsername());
        int total = 0;
        int totalcat = 0;
        int count = expensesList.size();
        for(Expense e: expensesList){
            total += e.getAmount();
            if(e.getCategory().equals(category)){
                totalcat += e.getAmount();
            }
        }
        if ((totalcat/total)*100 > percentage){
            Toast.makeText(this, "The expenses you already have exceed the specified budget, reset the budget", Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this, "Failed to save budget",Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
