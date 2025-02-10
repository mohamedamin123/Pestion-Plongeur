package com.example.plongeur.view.entreprise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.plongeur.R;
import com.example.plongeur.databinding.ActivityAddEntrepriseBinding;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.service.EntrepriseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class AddEntrepriseActivity extends AppCompatActivity {

    private ActivityAddEntrepriseBinding binding;
    private String id;
   // private EntrepriseController controller;
    private EntrepriseService service;
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
        id=getIntent().getStringExtra("id");
        service=new EntrepriseService();
        //controller=new ViewModelProvider(this).get(EntrepriseController.class);
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
        if(id==null) {
            Entreprise entreprise=(new Entreprise(name,tel,Integer.parseInt(nbrPlongeur)));
            service.ajouterEntreprise(entreprise,succ->{
                Log.d("Firestore", "Équipement ajouté avec succès !");
                Toast.makeText(this, "l'entreprise "+name+" ajouter avec succes", Toast.LENGTH_SHORT).show();
            },e->{
                Log.e("Firestore", "Erreur lors de l'ajout de l'équipement", e);
            });
        }
        else {
            Entreprise entreprise=new Entreprise(id,name,tel,Integer.parseInt(nbrPlongeur));
           //controller.update(entreprise);


            service.mettreAJourEntreprise(entreprise.getIdEntreprise(),entreprise,succ->{
                Log.d("Firestore", "Équipement mis à jour !");
                Toast.makeText(this, "l'entreprise "+name+" modifier avec succes", Toast.LENGTH_SHORT).show();
            },e->{
                Log.e("Firestore", "Erreur de mise à jour", e);
                Toast.makeText(this, "Erreur de mise à jour", Toast.LENGTH_SHORT).show();
            });
        }

        Intent intent = new Intent(this, ListEntrepriseActivity.class);
        startActivity(intent);
    }

    private void getData() {
        if (id!=null) {

            service.getEntrepriseById(id,entreprise->{
                if (entreprise!= null) {
                    binding.editNom.setText(entreprise.getNom());
                    binding.editTel.setText(entreprise.getTelephone());
                    binding.editNbr.setText(String.valueOf(entreprise.getNbrPlongeur()));
                }
            },e->{
                Toast.makeText(getApplicationContext(), "il y'a une error", Toast.LENGTH_SHORT).show();
            });

//
//            controller.findById(id).observe(this, entreprise -> {
//                if (entreprise!= null) {
//                    binding.editNom.setText(entreprise.getNom());
//                    binding.editTel.setText(entreprise.getTelephone());
//                    binding.editNbr.setText(String.valueOf(entreprise.getNbrPlongeur()));
//                }
//            });
        }
    }

}