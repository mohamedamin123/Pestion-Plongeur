package com.example.plongeur.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plongeur.databinding.ActivityMainBinding;
import com.example.plongeur.view.entreprise.ListEntrepriseActivity;
import com.example.plongeur.view.equipements.ListEquipmentsActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.btnViewEquipments.setOnClickListener(v -> toListEquipment());
        binding.btnEntreprise.setOnClickListener(v -> toListEntreprise());
    }

    private void toListEquipment() {
        Intent intent = new Intent(this, ListEquipmentsActivity.class);
        startActivity(intent);
    }
    private void toListEntreprise() {
        Intent intent = new Intent(this, ListEntrepriseActivity.class);
        startActivity(intent);
    }


}