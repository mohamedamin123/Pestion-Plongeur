package com.example.plongeur.view.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plongeur.R;
import com.example.plongeur.databinding.ActivityAddNouveauCompteBinding;
import com.example.plongeur.model.User;
import com.example.plongeur.service.UserService;
import com.example.plongeur.sharedPreferences.UserShared;
import com.example.plongeur.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AddNouveauCompteActivity extends AppCompatActivity {
    private ActivityAddNouveauCompteBinding binding;
    private FirebaseAuth mAuth;
    private UserService service;
    private String id;
    private UserShared shared;
    private ProgressDialog progressDialog; // Déclaration du ProgressDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNouveauCompteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialisation du ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Initialiser FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        service = new UserService();
        shared = new UserShared(this);
        id = getIntent().getStringExtra("id");
        getData();

        // Configurer le bouton de création de compte
        binding.btnCreateAccount.setOnClickListener(v -> createAccount());
        binding.btnRetour.setOnClickListener(v -> toMain());
    }

    private void getData() {
        if (id != null) {
            service.getUserById(id, user -> {
                if (user != null) {
                    binding.email.setText(user.getEmail());
                    binding.role.setSelection(user.getRole().equals("Lire seulement") ? 0 : 1);
                    binding.password.setVisibility(View.GONE);
                    binding.tvPassword.setVisibility(View.GONE);
                    binding.email.setFocusable(false);
                }
            }, e -> {
                Log.e("ERROR", e.getMessage());
            });
        }
    }

    private void toMain() {
        finish();
        Intent intent = new Intent(AddNouveauCompteActivity.this, ListeUserActivity.class);
        startActivity(intent);
    }

    private void createAccount() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        String role = binding.role.getSelectedItem().toString();

        if (email.isEmpty()) {
            binding.email.setError("L'email est requis");
            binding.email.requestFocus();
            return;
        }

        // Afficher le ProgressDialog pendant la création ou la mise à jour du compte
        progressDialog.show();

        if (id == null) { // Création d'un nouveau compte
            if (password.isEmpty()) {
                binding.password.setError("Le mot de passe est requis");
                binding.password.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        progressDialog.dismiss(); // Fermer le ProgressDialog une fois l'opération terminée
                        if (task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();
                            User user = new User(uid, email, role);
                            service.ajouterUserAvecId(user, success -> {
                                mAuth.signOut();
                                mAuth.signInWithEmailAndPassword(shared.getEmail(), shared.getPassword())
                                        .addOnCompleteListener(signInTask -> {
                                            if (signInTask.isSuccessful()) {
                                                Toast.makeText(this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                                                toMain();
                                            } else {
                                                Toast.makeText(this, "Erreur lors de la reconnexion : " + signInTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }, e -> {
                                progressDialog.dismiss(); // Fermer en cas d'erreur
                                Log.e("ERROR", "Échec de l'ajout : " + e.getMessage());
                            });
                        } else {
                            progressDialog.dismiss(); // Fermer en cas d'erreur
                            Toast.makeText(this, "Erreur de création : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else { // Modification d'un compte existant
            User user = new User(id, email, role);
            service.mettreAJourUser(id, user, success -> {
                progressDialog.dismiss(); // Fermer après mise à jour
                Toast.makeText(this, "Compte mis à jour avec succès", Toast.LENGTH_SHORT).show();
                toMain();
            }, e -> {
                progressDialog.dismiss(); // Fermer en cas d'erreur
                Toast.makeText(this, "Erreur lors de la mise à jour : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Échec de la mise à jour : " + e.getMessage());
            });
        }
    }
}
