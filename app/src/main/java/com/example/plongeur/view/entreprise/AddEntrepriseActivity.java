package com.example.plongeur.view.entreprise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.plongeur.R;
import com.example.plongeur.controller.EntrepriseController;
import com.example.plongeur.databinding.ActivityAddEntrepriseBinding;
import com.example.plongeur.model.Entreprise;

import java.util.ArrayList;

public class AddEntrepriseActivity extends AppCompatActivity {

    private ActivityAddEntrepriseBinding binding;
    private int id;
    private EntrepriseController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddEntrepriseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //recevoir les donnees
        id=getIntent().getIntExtra("id",-1);
        controller=new ViewModelProvider(this).get(EntrepriseController.class);
        getData();
        binding.btnSave.setOnClickListener(v->ajouterEntreprise());
        binding.btnRetour.setOnClickListener(v->toListEntreprise());

    }

    private void toListEntreprise() {
        Intent intent = new Intent(this, ListEntrepriseActivity.class);
        startActivity(intent);
    }
    private void ajouterEntreprise() {
        String name = binding.editNom.getText().toString();
        String tel = binding.editTel.getText().toString();
        String nbrPlongeur = binding.editNbr.getText().toString();
        if(name.isEmpty() || tel.isEmpty() || nbrPlongeur.isEmpty()) {
            Toast.makeText(this, "Remplir tous les champs vide", Toast.LENGTH_SHORT).show();
            return;
        } else if(tel.length()!=8) {
            Toast.makeText(this, "Numero de telephone invalide", Toast.LENGTH_SHORT).show();
            return;
        }
        if(id==-1) {
            Toast.makeText(this, "l'entreprise "+name+" ajouter avec succes", Toast.LENGTH_SHORT).show();
            controller.insert(new Entreprise(name,tel,Integer.parseInt(nbrPlongeur)));
        }
        else {
            Toast.makeText(this, "l'entreprise "+name+" modifier avec succes", Toast.LENGTH_SHORT).show();
            Entreprise entreprise=new Entreprise(id,name,tel,Integer.parseInt(nbrPlongeur));
           controller.update(entreprise);
        }

        Intent intent = new Intent(this, ListEntrepriseActivity.class);
        startActivity(intent);
    }

    private void getData() {
        if (id!=-1) {
            controller.findById(id).observe(this, entreprise -> {
                if (entreprise!= null) {
                    binding.editNom.setText(entreprise.getNom());
                    binding.editTel.setText(entreprise.getTelephone());
                    binding.editNbr.setText(String.valueOf(entreprise.getNbrPlongeur()));
                }
            });
        }
    }

}