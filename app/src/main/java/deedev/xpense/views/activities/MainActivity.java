package deedev.xpense.views.activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import deedev.xpense.adapters.TransactionsAdapter;
import deedev.xpense.models.Transaction;
import deedev.xpense.utils.Constants;
import deedev.xpense.utils.Helper;
import deedev.xpense.viewmodels.MainViewModel;
import deedev.xpense.views.fragments.AddTransactionFragment;
import deedev.xpense.R;
import deedev.xpense.databinding.ActivityMainBinding;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    Calendar calendar;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

        Constants.setCategories();

        calendar = Calendar.getInstance();
        updateDate();
        binding.nextDateButton.setOnClickListener(c -> {
            calendar.add(Calendar.DATE, 1);
            updateDate();
        });
        binding.backDateButton.setOnClickListener(c -> {
            calendar.add(Calendar.DATE, -1);
            updateDate();
        });
        binding.floatingActionButton2.setOnClickListener(c ->{
            new AddTransactionFragment().show(getSupportFragmentManager(), null);

        });



        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
        viewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this, transactions);
                binding.transactionsList.setAdapter(transactionsAdapter);

            }

        });

        viewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeLbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseLbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.totalAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.getTransactions(calendar);







    }



    void updateDate(){
        binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        viewModel.getTransactions(calendar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}