package deedev.xpense.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import deedev.xpense.R;
import deedev.xpense.databinding.RowTransactionsBinding;
import deedev.xpense.models.Category;
import deedev.xpense.models.Transaction;
import deedev.xpense.utils.Constants;
import deedev.xpense.utils.Helper;
import deedev.xpense.views.activities.MainActivity;
import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    private final Context context;
    private final RealmResults<Transaction> transactions;

    public TransactionsAdapter(Context context, RealmResults<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_transactions, parent, false);
        return new TransactionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        if (transaction != null) {
            holder.binding.transactionCategory.setText(transaction.getCategory());
            holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
            holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
            holder.binding.accountLbl.setText(transaction.getAccount());

            Category transactionCategory = Constants.getCategoryDetails(transaction.getCategory());
            if (transactionCategory != null) {
                holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());
                holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));
            }

            holder.binding.accountLbl.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction.getAccount())));

            // Set text color based on transaction type
            if (Constants.INCOME.equals(transaction.getType())) {
                holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor));
            } else if (Constants.EXPENSE.equals(transaction.getType())) {
                holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor));
            }

            // Long-click listener to delete transaction
            holder.itemView.setOnLongClickListener(view -> {
                showDeleteConfirmationDialog(transaction);
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    private void showDeleteConfirmationDialog(Transaction transaction) {
        AlertDialog deleteDialog = new AlertDialog.Builder(context)
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).viewModel.deleteTransaction(transaction);
                    }
                })
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
        deleteDialog.show();
    }

    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {
        RowTransactionsBinding binding;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionsBinding.bind(itemView);
        }
    }
}
