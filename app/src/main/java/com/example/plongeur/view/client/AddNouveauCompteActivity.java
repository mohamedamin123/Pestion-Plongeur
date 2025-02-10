package com.example.plongeur.view.client;

import static com.example.plongeur.view.client.LoginActivity.passwordUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plongeur.R;
import com.example.plongeur.databinding.ActivityAddNouveauCompteBinding;
import com.example.plongeur.model.User;
import com.example.plongeur.service.UserService;
import com.example.plongeur.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AddNouveauCompteActivity extends AppCompatActivity {
    private ActivityAddNouveauCompteBinding binding;
    private FirebaseAuth mAuth;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNouveauCompteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Initialiser FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        service = new UserService();

        // Configurer le bouton de création de compte
        binding.btnCreateAccount.setOnClickListener(v -> createAccount());
        binding.btnRetour.setOnClickListener(v -> toMain());
    }

    private void toMain() {
        // Rediriger vers l'écran de connexion (LoginActivity)
        finish();
        Intent intent = new Intent(AddNouveauCompteActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void createAccount() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        String role = binding.role.getSelectedItem().toString();

        // Sauvegarder les identifiants de l'utilisateur actuel
        String currentUserEmail = mAuth.getCurrentUser().getEmail();

        if (email.isEmpty()) {
            binding.email.setError("L'email est requis");
            binding.email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.password.setError("Le mot de passe est requis");
            binding.password.requestFocus();
            return;
        }

        // Création du compte utilisateur sans rester connecté
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Ajouter l'utilisateur dans la base de données
                        User user = new User(email, role);
                        service.ajouterUser(user, success -> {
                            // 🔹 Se reconnecter avec l'ancien compte après l'ajout du nouvel utilisateur
                            mAuth.signOut(); // Déconnexion du nouveau compte
                            mAuth.signInWithEmailAndPassword(currentUserEmail, passwordUser)
                                    .addOnCompleteListener(signInTask -> {
                                        if (signInTask.isSuccessful()) {
                                            Toast.makeText(AddNouveauCompteActivity.this, "Compte créé avec succès et retour à l'admin", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AddNouveauCompteActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(AddNouveauCompteActivity.this, "Erreur lors de la reconnexion : " + signInTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }, e -> {});
                    } else {
                        Log.d("erreur", task.getException().getMessage());
                        Toast.makeText(AddNouveauCompteActivity.this, "Erreur lors de la création du compte : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
