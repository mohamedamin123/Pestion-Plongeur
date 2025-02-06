package com.example.plongeur.view.entreprise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.plongeur.R;
import com.example.plongeur.databinding.ActivityListEntrepriseBinding;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.view.MainActivity;
import com.example.plongeur.view.equipements.ListEquipmentsActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListEntrepriseActivity extends AppCompatActivity implements EntrepriseAdapter.OnItemClickListener {

    private ActivityListEntrepriseBinding binding;
    private EntrepriseAdapter adapter;
    private List<Entreprise> entreprises;
    private List<Entreprise> filtred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListEntrepriseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        entreprises = new ArrayList<>();
        filtred = new ArrayList<>();

        getData();
        adapter = new EntrepriseAdapter(entreprises, this, this);

        binding.recylerEntreprise.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerEntreprise.setAdapter(adapter);

        binding.btnAjouter.setOnClickListener(v -> ajouterEntreprise());
        binding.btnAnnuler.setOnClickListener(v -> toMain());

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

    private void filterUsers(String query) {
        if (query.isEmpty()) {
            filtred.clear();
            filtred.addAll(entreprises);
        } else {
            filtred = entreprises.stream()
                    .filter(client -> client.getNom().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        adapter.updateList(filtred);
    }

    private void getData() {
        entreprises.add(new Entreprise(1, "TTF", "95147455", 10));
        entreprises.add(new Entreprise(2, "ganaXgana", "95147455", 7));
        entreprises.add(new Entreprise(3, "ccc", "95147455", 5));
    }

    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void ajouterEntreprise() {
        Intent intent = new Intent(this, AddEntrepriseActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Entreprise entreprise) {
        showEntrepriseOptionsDialog(entreprise);
    }

    private void showEntrepriseOptionsDialog(Entreprise entreprise) {
        new MaterialAlertDialogBuilder(this, R.style.CustomDialogStyle)
                .setTitle("Options")
                .setMessage("Que voulez-vous faire avec " + entreprise.getNom() + " ?")
                .setBackground(getResources().getDrawable(R.drawable.dialog_background, null))
                .setPositiveButton("🔍 Voir équipements", (dialog, which) -> voirEquipements(entreprise))
                .setNeutralButton("✏ Modifier", (dialog, which) -> modifierEntreprise(entreprise))
                .setNegativeButton("🗑 Supprimer", (dialog, which) -> supprimerEntreprise(entreprise))
                .show();
    }


    private void voirEquipements(Entreprise entreprise) {
        Intent intent = new Intent(this, ListEquipmentsActivity.class);
        intent.putExtra("entrepriseId", entreprise.getIdEntreprise());
        startActivity(intent);
    }

    private void modifierEntreprise(Entreprise entreprise) {
        Intent intent = new Intent(this, AddEntrepriseActivity.class);
        intent.putExtra("id", entreprise.getIdEntreprise());
        startActivity(intent);
    }

    private void supprimerEntreprise(Entreprise entreprise) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer " + entreprise.getNom())
                .setMessage("Êtes-vous sûr de vouloir supprimer cette entreprise ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    entreprises.remove(entreprise);
                    adapter.updateList(entreprises);
                    Toast.makeText(this, "Entreprise supprimée", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
