package deedev.xpense.views.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import deedev.xpense.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);

        videoView.setVideoURI(uri);
        videoView.setOnCompletionListener(mediaPlayer -> {
            // Once the video finishes playing, check login status
            checkLoginStatus();
        });

        videoView.start();
    }

    private void checkLoginStatus() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            Log.d(TAG, "User is signed in: " + currentUser.getEmail());
            // Navigate to main screen
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Log.d(TAG, "No user is signed in.");
            // Navigate to login screen
            startActivity(new Intent(this, LogInActivity.class));
        }

        finish(); // Close splash so back button won't return to it
    }
}
