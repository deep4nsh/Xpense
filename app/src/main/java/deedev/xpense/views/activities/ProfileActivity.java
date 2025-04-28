package deedev.xpense.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import deedev.xpense.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView userPhoneTextView;
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);  // Replace with your actual layout

        // Initialize the views
        userNameTextView = findViewById(R.id.profileName);
        userEmailTextView = findViewById(R.id.profileEmail);
        userPhoneTextView = findViewById(R.id.profilePhone);
        profileImageView = findViewById(R.id.profileImage);

        // Get the data passed from MainActivity
        Intent intent = getIntent();
        String name = intent.getStringExtra("userName");
        String email = intent.getStringExtra("userEmail");
        String phone = intent.getStringExtra("userPhone");
        String photoUrl = intent.getStringExtra("userPhotoUrl");

        // Set the data to the views
        userNameTextView.setText(name);
        userEmailTextView.setText(email);
        userPhoneTextView.setText(phone);

        // Load the profile image using Glide
        if (photoUrl != null && !photoUrl.isEmpty()) {
            Glide.with(this)
                    .load(photoUrl)
                    .circleCrop()
                    .into(profileImageView);
        }
    }
}
