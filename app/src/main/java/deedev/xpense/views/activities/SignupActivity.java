package deedev.xpense.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import deedev.xpense.R;

public class SignupActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, nameEditText, phoneEditText;
    private Button signUpButton;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        nameEditText = findViewById(R.id.editTextName);
        phoneEditText = findViewById(R.id.editTextPhone);
        signUpButton = findViewById(R.id.buttonSignup);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signUpButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Enter valid email");
                return;
            }
            if (TextUtils.isEmpty(password) || password.length() < 6) {
                passwordEditText.setError("Password must be 6+ characters");
                return;
            }
            if (TextUtils.isEmpty(name)) {
                nameEditText.setError("Enter name");
                return;
            }

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("uid", user.getUid());
                            userMap.put("name", name);
                            userMap.put("email", email);
                            userMap.put("phone", phone);

                            firestore.collection("users").document(user.getUid())
                                    .set(userMap)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this, LogInActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(this, "Firestore Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                    );
                        } else {
                            Toast.makeText(this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
