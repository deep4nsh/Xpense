package deedev.xpense.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import deedev.xpense.R;
import deedev.xpense.databinding.RowTransactionsBinding;
import deedev.xpense.models.Category;
import deedev.xpense.models.Transaction;
import deedev.xpense.utils.Constants;
import deedev.xpense.utils.Helper;
import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    Context context;
    RealmResults<Transaction> transactions;

    public TransactionsAdapter(Context context, RealmResults<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;

    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transactions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.binding.transactionCategory.setText(transaction.getCategory());
        holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountLbl.setText(transaction.getAccount());

        Category transactionCategory = Constants.getCategoryDetails(transaction.getCategory());

        holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));


        holder.binding.accountLbl.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction.getAccount())));

        if (transaction.getType().equals(Constants.INCOME)){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor));
        }else if (transaction.getType().equals(Constants.EXPENSE)){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor));
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionsViewHolder extends RecyclerView.ViewHolder {

        RowTransactionsBinding binding;
        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionsBinding.bind(itemView);
        }
    }
}
