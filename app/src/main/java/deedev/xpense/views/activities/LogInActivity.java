package deedev.xpense.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import deedev.xpense.R;
import deedev.xpense.viewmodels.User; // Make sure this is your model class path

public class LogInActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private TextView signUpText;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        signInButton = findViewById(R.id.buttonSignIn);
        signUpText = findViewById(R.id.textSignUp);

        // Firebase initialization
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Check if user is already signed in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // If user is already signed in, navigate directly to the MainActivity
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish(); // Prevent going back to the login screen
        }

        // Email/Password Login
        signInButton.setOnClickListener(view -> signInUser());

        // Switch to Signup Activity
        signUpText.setOnClickListener(v -> openSignUpActivity());

        // Google Sign-In setup
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Google Button Click
        findViewById(R.id.googleSignInButton).setOnClickListener(v -> signInWithGoogle());
    }

    private void signInUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LogInActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);  // Use 101 as the RC_SIGN_IN code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    // Save user info to Firestore (optional)
                    User user = new User(
                            firebaseUser.getDisplayName(),
                            firebaseUser.getEmail(),
                            "" // phone not available via Google Sign-In
                    );

                    firestore.collection("Users")
                            .document(firebaseUser.getUid())
                            .set(user)
                            .addOnSuccessListener(unused -> {
                                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                finish();
                            });
                }
            } else {
                Toast.makeText(LogInActivity.this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openSignUpActivity() {
        Intent intent = new Intent(LogInActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
