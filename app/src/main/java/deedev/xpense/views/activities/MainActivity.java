package deedev.xpense.views.activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import deedev.xpense.views.fragments.AddTransactionFragment;
import deedev.xpense.R;
import deedev.xpense.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

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

    }

    void updateDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        binding.currentDate.setText(dateFormat.format(calendar.getTime()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}