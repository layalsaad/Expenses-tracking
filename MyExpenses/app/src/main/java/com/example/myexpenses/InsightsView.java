package com.example.myexpenses;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class InsightsView extends AppCompatActivity {
    private Button btnBack;
    private DatabaseHelper db;
    private TextView tv;
    private SharedPrefManager shpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insights_view);

        db = new DatabaseHelper(this);
        btnBack = findViewById(R.id.btnback);
        tv = findViewById(R.id.txtviewinsights);
        List<String> insights = db.addInsights();

        for (String insight : insights) {
            tv.setText(insight+"\n");
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
