package com.example.plongeur.view.equipements;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.plongeur.R;
import com.example.plongeur.controller.EquipementController;
import com.example.plongeur.databinding.ActivityListEquipmentsBinding;
import com.example.plongeur.model.Equipment;
import com.example.plongeur.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ListEquipmentsActivity extends AppCompatActivity {

    private ActivityListEquipmentsBinding binding;
    private EquipmentAdapter adapter;
    private List<Equipment>equipments;
    private EquipementController controller;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListEquipmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        equipments = new ArrayList<>();
        controller=new ViewModelProvider(this).get(EquipementController.class);;
         id=getIntent().getIntExtra("id",-1);


        getData();
        changeTitre();


        adapter = new EquipmentAdapter(equipments,this);

        binding.recylerEquipments.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerEquipments.setAdapter(adapter);

        binding.btnAnnuler.setOnClickListener(v->toMain());
        binding.btnAjouter.setOnClickListener(v->save());
    }

    private void changeTitre() {
        String nom=getIntent().getStringExtra("nom");
        if(nom!=null){
            binding.titre.setText("Équipements de l'entreprise : "+nom);
        }
    }


    private void save() {
        for (int i = 0; i < equipments.size(); i++) {
            Equipment equipment = equipments.get(i);
            controller.update(equipment); // Met à jour chaque équipement dans la base de données
        }

        // Afficher un message de confirmation
        Toast.makeText(this, "Modifications enregistrées avec succès", Toast.LENGTH_SHORT).show();
        toMain();
    }


    private void toMain() {
       finish();
    }

    private void getData() {

        if(id==-1) {
            // Charger uniquement les équipements qui ne sont pas associés à une entreprise
            controller.findStockPersonnel().observe(this, new Observer<List<Equipment>>() {
                @Override
                public void onChanged(List<Equipment> stockEquipments) {
                    equipments.clear();
                    if (stockEquipments.isEmpty()) {

                        // Ajouter des équipements par défaut si le stock est vide
                        ajouterEquipementsParDefaut();
                    } else {
                        equipments.addAll(stockEquipments);

                    }
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            controller.findByIdEntreprise(id).observe(this, new Observer<List<Equipment>>() {
                @Override
                public void onChanged(List<Equipment> equipment) {
                    equipments.clear();
                    if(equipment.isEmpty()) {
                        ajouterEquipementsParDefaut();
                    }else {
                        equipments.addAll(equipment);
                    }

                    adapter.notifyDataSetChanged();
                }
            });
        }

    }

    // Insérer des équipements par défaut dans le stock personnel
    private void ajouterEquipementsParDefaut() {
        Equipment[] defaultEquipments = {
                new Equipment("Gilet", R.drawable.gilet),
                new Equipment("Détendeur", R.drawable.detendeur),
                new Equipment("Masque", R.drawable.mask),
                new Equipment("Palmes", R.drawable.palm),
                new Equipment("Bottes", R.drawable.boot),
                new Equipment("Bouteille", R.drawable.bouteille),
                new Equipment("Combinaison", R.drawable.combinaison),
                new Equipment("Ceinture", R.drawable.ceinture)
        };

        for (Equipment equipment : defaultEquipments) {
            if(id!=-1)
                equipment.setIdEntreprise(id);
            controller.insert(equipment);
        }
    }

}