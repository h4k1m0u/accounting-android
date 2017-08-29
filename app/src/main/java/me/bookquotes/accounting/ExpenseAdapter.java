package me.bookquotes.accounting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by h4k1m on 29/08/2017.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Expense> mExpenses;

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView mDescriptionTextView;
        TextView mAmountTextView;
        TextView mDateTextView;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.expense_description);
            mAmountTextView = (TextView) itemView.findViewById(R.id.expense_amount);
            mDateTextView = (TextView) itemView.findViewById(R.id.expense_date);
        }
    }

    public ExpenseAdapter(List<Expense> expenses) {
        mExpenses = expenses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate an expense row
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row, parent, false);
        viewHolder = new ExpenseViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // update expense row with expense object
        Expense expense = mExpenses.get(position);
        ExpenseViewHolder expenseViewHolder = (ExpenseViewHolder) holder;

        expenseViewHolder.mDescriptionTextView.setText(expense.getDescription());
        expenseViewHolder.mDateTextView.setText(expense.getDate());
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
        expenseViewHolder.mAmountTextView.setText(format.format(expense.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mExpenses.size();
    }
}
