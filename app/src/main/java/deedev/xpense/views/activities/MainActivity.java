package deedev.xpense.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

import deedev.xpense.R;
import deedev.xpense.databinding.ActivityMainBinding;
import deedev.xpense.utils.Constants;
import deedev.xpense.viewmodels.MainViewModel;
import deedev.xpense.views.fragments.StatsFragment;
import deedev.xpense.views.fragments.TransactionsFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;

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

        // Set initial fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new TransactionsFragment());
        transaction.commit();

        // Bottom navigation item selection listener
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId() == R.id.transactions) {
                    getSupportFragmentManager().popBackStack();
                } else if (item.getItemId() == R.id.stats) {
                    transaction.replace(R.id.content, new StatsFragment());
                    transaction.addToBackStack(null);
                }
                transaction.commit();
                return true;
            }
        });

        // Load Google user profile data
        loadGoogleUserProfile();
    }

    public void getTransactions() {
        viewModel.getTransactions(calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadGoogleUserProfile() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String name = account.getDisplayName();
            String email = account.getEmail();
            Uri photoUrl = account.getPhotoUrl();

            // Show a welcome toast
            Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show();

            // Passing data to ProfileActivity
            Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
            profileIntent.putExtra("userName", name);
            profileIntent.putExtra("userEmail", email);
            profileIntent.putExtra("userPhotoUrl", photoUrl != null ? photoUrl.toString() : "");
            startActivity(profileIntent);
        } else {
            // If no Google account is signed in, show a toast message
            Toast.makeText(this, "No Google account signed in", Toast.LENGTH_SHORT).show();

            // Redirect to login or sign-up screen if necessary
            // Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            // startActivity(loginIntent);
        }
    }
}
