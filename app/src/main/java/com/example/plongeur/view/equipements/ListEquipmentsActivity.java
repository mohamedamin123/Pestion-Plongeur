package com.example.plongeur.view.equipements;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.plongeur.R;
import com.example.plongeur.databinding.ActivityListEquipmentsBinding;
import com.example.plongeur.model.Equipment;
import com.example.plongeur.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ListEquipmentsActivity extends AppCompatActivity {

    private ActivityListEquipmentsBinding binding;
    private EquipmentAdapter adapter;
    private List<Equipment>equipments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListEquipmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
        adapter = new EquipmentAdapter(equipments,this);

        binding.recylerEquipments.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerEquipments.setAdapter(adapter);

        binding.btnAnnuler.setOnClickListener(v->toMain());

    }

    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getData() {
        // TODO : récupérer les données des équipements et les passer à l'adapter
        equipments = new ArrayList<>();
        equipments.add(new Equipment(1, "Gilet", R.drawable.gilet));
        equipments.add(new Equipment(2, "Détendeur", R.drawable.detendeur));
        equipments.add(new Equipment(3, "Masque", R.drawable.mask));
        equipments.add(new Equipment(4, "Palmes", R.drawable.palm));
        equipments.add(new Equipment(5, "Bottes", R.drawable.boot));
        equipments.add(new Equipment(6, "Bouteille", R.drawable.bouteille));
        equipments.add(new Equipment(7, "Combinaison", R.drawable.combinaison));
        equipments.add(new Equipment(8, "Ceinture", R.drawable.ceinture));
    }

}