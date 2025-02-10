package com.example.plongeur.view.client;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.plongeur.R;
import com.example.plongeur.databinding.ActivityListeUserBinding;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.User;
import com.example.plongeur.service.UserService;
import com.example.plongeur.view.MainActivity;
import com.example.plongeur.view.entreprise.AddEntrepriseActivity;
import com.example.plongeur.view.entreprise.EntrepriseAdapter;
import com.example.plongeur.view.equipements.ListEquipmentsActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListeUserActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {
    private ActivityListeUserBinding binding;
    private UserAdapter adapter;
    private List<User> users,filtred;
    private UserService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityListeUserBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        users=new ArrayList<>();
        filtred=new ArrayList<>();
        service=new UserService();
        getData();
        adapter = new UserAdapter(users, this, this);

        binding.recylerEntreprise.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerEntreprise.setAdapter(adapter);

        binding.btnAnnuler.setOnClickListener(e->retour());
        binding.btnAjouter.setOnClickListener(e->ajouterUser());


        // Configurer la barre de recherche
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }


    private void retour() {
        finish();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void ajouterUser() {
        Intent intent=new Intent(this, AddNouveauCompteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        service.getAllUsers(users1 -> {
            ListeUserActivity.this.users.clear();
            ListeUserActivity.this.users.addAll(users1);
            adapter.updateList(users);

        }, e -> {
            // TODO: Handle error
            Toast.makeText(this, "erreur dans l'affichage", Toast.LENGTH_SHORT).show();
        });
    }

    private void filterUsers(String query) {
        if (query.isEmpty()) {
            filtred.clear();
            filtred.addAll(users);
        } else {
            filtred = users.stream()
                    .filter(client -> client.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                            client.getRole().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        adapter.updateList(filtred);
    }

    @Override
    public void onItemClick(User item) {
        showUserOptionsDialog(item);
    }

    private void showUserOptionsDialog(User user) {
        if(user.getRole().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Vous ne pouvez pas changer vos données",Toast.LENGTH_SHORT).show();
        }else {
                modifierUser(user);
        }

    }


    private void modifierUser(User user) {
        Intent intent = new Intent(this, AddNouveauCompteActivity.class);
        intent.putExtra("id", user.getIdUser());
        startActivity(intent);
    }

//    private void supprimerUser(User user) {
//        new AlertDialog.Builder(this)
//                .setTitle("Supprimer " + user.getEmail())
//                .setMessage("Êtes-vous sûr de vouloir supprimer cette entreprise ?")
//                .setPositiveButton("Oui", (dialog, which) -> {
//                    users.remove(user);
//                    adapter.updateList(users);
//                    // controller.deleteById(entreprise.getIdEntreprise());
//                    service.supprimerUser(user.getIdUser(),succ->{
//                        Log.d("Firestore", "user supprimé !");
//                        Toast.makeText(this, "user supprimée", Toast.LENGTH_SHORT).show();
//                    },e->{
//                        Log.e("Firestore", "Erreur lors de la suppression", e);
//                        Toast.makeText(this, "Erreur lors de la suppression :"+user.getEmail(), Toast.LENGTH_SHORT).show();
//
//                    });
//
//                })
//                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
//                .show();
//    }

}