package deedev.xpense.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import deedev.xpense.R;
import deedev.xpense.adapters.AccountsAdapter;
import deedev.xpense.adapters.CategoryAdapter;
import deedev.xpense.databinding.FragmentAddTransactionBinding;
import deedev.xpense.databinding.ListDialogBinding;
import deedev.xpense.models.Account;
import deedev.xpense.models.Category;
import deedev.xpense.models.Transaction;
import deedev.xpense.utils.Constants;
import deedev.xpense.utils.Helper;

public class AddTransactionFragment extends BottomSheetDialogFragment {



    public AddTransactionFragment() {
        // Required empty public constructor

    }

    public static AddTransactionFragment newInstance(String param1, String param2) {
        AddTransactionFragment fragment = new AddTransactionFragment();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddTransactionBinding binding;
    Transaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater);

        transaction = new Transaction();
        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector_white));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));

            transaction.setType(Constants.INCOME);

        });

        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector_white));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));

            transaction.setType(Constants.EXPENSE);


        });


        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    calendar.set(calendar.MONTH, datePicker.getMonth());
                    calendar.set(calendar.YEAR, datePicker.getYear());
                    String dateToShow= Helper.formatDate(calendar.getTime());

                    binding.date.setText(dateToShow);

                    transaction.setDate(calendar.getTime());
                });
                datePickerDialog.show();
            }
        });

        binding.category.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());

            ArrayList<Category> categories = new ArrayList<>();
            categories.add(new Category("Salary", R.drawable.salary, R.color.category1));
            categories.add(new Category("Business", R.drawable.business, R.color.category2));
            categories.add(new Category("Investment", R.drawable.investment,R.color.category3));
            categories.add(new Category("Loan", R.drawable.loan,R.color.category4));
            categories.add(new Category("Rent", R.drawable.rent, R.color.category5));
            categories.add(new Category("Other", R.drawable.other, R.color.category6));

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCategoryName());
                    transaction.setCategory(category.getCategoryName());
                    categoryDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);

            categoryDialog.show();


        });

        binding.account.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(dialogBinding.getRoot());

            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0, "Cash"));
            accounts.add(new Account(0, "Bank"));
            accounts.add(new Account(0, "UPI"));
            accounts.add(new Account(0, "Other"));

            AccountsAdapter accountsAdapter = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.account.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());

                    accountsDialog.dismiss();
                }
                });
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.setAdapter(accountsAdapter);
            dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            accountsDialog.show();

        });

        binding.saveTransactionBtn.setOnClickListener(c -> {
            double amount =Double.parseDouble(binding.amount.getText().toString());
            String note = binding.note.getText().toString();

            if(transaction.getType()==Constants.EXPENSE){
                amount*=-1;

            }else {
                transaction.setAmount(amount);
            }
            transaction.setNote(note);
        });

        return binding.getRoot();
    }
}