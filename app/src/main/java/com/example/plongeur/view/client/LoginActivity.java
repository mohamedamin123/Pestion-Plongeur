package com.example.plongeur.view.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plongeur.databinding.ActivityLoginBinding;
import com.example.plongeur.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    static String passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Initialisation du ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setCancelable(false);

        binding.btnLogin.setOnClickListener(v -> connecter());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void connecter() {
        String email = binding.email.getText().toString().trim();
        passwordUser = binding.password.getText().toString().trim();

        if (email.isEmpty()) {
            binding.email.setError("L'email est requis.");
            binding.email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.setError("L'email n'est pas valide.");
            binding.email.requestFocus();
            return;
        }

        if (passwordUser.isEmpty()) {
            binding.password.setError("Le mot de passe est requis.");
            binding.password.requestFocus();
            return;
        }

        if (passwordUser.length() < 6) {
            binding.password.setError("Le mot de passe doit contenir au moins 6 caractères.");
            binding.password.requestFocus();
            return;
        }

        progressDialog.show(); // Affichage du chargement

        mAuth.signInWithEmailAndPassword(email, passwordUser)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss(); // Fermeture du chargement
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Erreur : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
