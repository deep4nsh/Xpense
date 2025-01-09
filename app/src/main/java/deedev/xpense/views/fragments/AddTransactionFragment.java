package deedev.xpense.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import deedev.xpense.R;
import deedev.xpense.databinding.FragmentAddTransactionBinding;
import deedev.xpense.models.Category;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater);

        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector_white));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));

        });

        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector_white));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));

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
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM, yyyy");
                    String dateToShow=dateFormat.format(calendar.getTime());

                    binding.date.setText(dateToShow);
                });
                datePickerDialog.show();
            }
        });

        binding.category.setOnClickListener(c -> {
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
        });
        return binding.getRoot();
    }
}