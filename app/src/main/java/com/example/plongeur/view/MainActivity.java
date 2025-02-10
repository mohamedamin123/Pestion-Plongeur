package com.example.plongeur.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plongeur.databinding.ActivityMainBinding;
import com.example.plongeur.model.User;
import com.example.plongeur.service.UserService;
import com.example.plongeur.sharedPreferences.UserShared;
import com.example.plongeur.view.client.AddNouveauCompteActivity;
import com.example.plongeur.view.client.ListeUserActivity;
import com.example.plongeur.view.client.LoginActivity;
import com.example.plongeur.view.entreprise.ListEntrepriseActivity;
import com.example.plongeur.view.equipements.ListEquipmentsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private User userCourant;
    private UserService service;
    private FirebaseUser user;
    private UserShared shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        service = new UserService();
        shared=new UserShared(this);

        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        getData();

        binding.btnViewEquipments.setOnClickListener(v -> toListEquipment());
        binding.btnEntreprise.setOnClickListener(v -> toListEntreprise());
        binding.btnDeconnexion.setOnClickListener(v -> deconnexion());
        binding.btnListeUser.setOnClickListener(v -> toListeUser());
    }

    private void getData() {
        userCourant=new User();
        userCourant.setEmail(shared.getEmail());
        userCourant.setRole(shared.getRole());
        userCourant.setIdUser(shared.getId());
//        service.findByEmail(user.getEmail(), user1 -> {
//            userCourant = user1;
//
//        }, e -> {
//            // Handle failure (optional)
//            Toast.makeText(MainActivity.this, "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        });
    }

    private void toListeUser() {

        if(userCourant!=null) {
            if (userCourant.getRole().equals("admin")) {
                startActivity(new Intent(MainActivity.this, ListeUserActivity.class));
            } else {
                Toast.makeText(MainActivity.this, "Vous n'avez pas le droit d'accéder à cette page", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "reconnect a votre compte", Toast.LENGTH_SHORT).show();
            deconnexion();
        }

    }

    private void deconnexion() {
        shared.clearUser();
        mAuth.signOut();
        Toast.makeText(MainActivity.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void toListEquipment() {
        Intent intent = new Intent(this, ListEquipmentsActivity.class);
        intent.putExtra("id", "MonEntreprise");
        intent.putExtra("nom", "Mon Stock");
        startActivity(intent);
    }

    private void toListEntreprise() {
        startActivity(new Intent(this, ListEntrepriseActivity.class));
    }
}
