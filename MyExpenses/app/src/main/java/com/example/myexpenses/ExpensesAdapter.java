package com.example.myexpenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder> {
    private List<String> expensesList;

    public ExpensesAdapter(List<String> expensesList) {
        this.expensesList = expensesList;
    }

    @NonNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list, parent, false);
        return new ExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesViewHolder holder, int position) {
        String expense = expensesList.get(position);
        holder.txtExpense.setText(expense);
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    public static class ExpensesViewHolder extends RecyclerView.ViewHolder {

        TextView txtExpense;

        public ExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExpense = itemView.findViewById(R.id.txtexpense);
        }
    }
}
