package com.example.plongeur.view.equipements;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.plongeur.R;
import com.example.plongeur.databinding.ActivityListEquipmentsBinding;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.Equipment;
import com.example.plongeur.service.EntrepriseService;
import com.example.plongeur.service.EquipmentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListEquipmentsActivity extends AppCompatActivity implements EquipmentAdapter.OnItemClickListener {

    private ActivityListEquipmentsBinding binding;
    private EquipmentAdapter adapter;
    private List<Equipment> equipments, anciens;
    private EquipmentService service;
    private EntrepriseService entrepriseService;
    private String id;
    private Entreprise entrepriseUtilise;
    String name="MonEntreprise";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListEquipmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        equipments = new ArrayList<>();
        anciens = new ArrayList<>();
        service = new EquipmentService();
        entrepriseService = new EntrepriseService();
        id = getIntent().getStringExtra("id");

        // Initialiser l'adaptateur une seule fois
        adapter = new EquipmentAdapter(equipments, this,this);
        binding.recylerEquipments.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerEquipments.setAdapter(adapter);

        getData();
        changeTitre();

        binding.btnAnnuler.setOnClickListener(v -> toMain());
    }

    private void changeTitre() {
        String nom = getIntent().getStringExtra("nom");
        if (nom != null) {
            binding.titre.setText(nom);
        }
    }


    private void toMain() {
        finish();
    }
    private List<Equipment> getEquipementsParDefaut() {
        return Arrays.asList(
                new Equipment("Gilet", R.drawable.gilet),
                new Equipment("Détendeur", R.drawable.detendeur),
                new Equipment("Masque", R.drawable.mask),
                new Equipment("Palmes", R.drawable.palm),
                new Equipment("Bottes", R.drawable.boot),
                new Equipment("Bouteille", R.drawable.bouteille),
                new Equipment("Combinaison", R.drawable.combinaison),
                new Equipment("Ceinture", R.drawable.ceinture)
        );
    }

    private void getData() {
        entrepriseService.getEntrepriseById(id, entreprise -> {
            if (entreprise != null) {
                Log.d("getData", "Entreprise trouvée: " + entreprise.getNom());
                entrepriseUtilise=entreprise;
                // Vérifier si `equipment` est null ou vide
                if (entreprise.getEquipment() == null || entreprise.getEquipment().isEmpty()) {
                    Log.d("getData", "Aucun équipement trouvé, ajout des équipements par défaut...");
                    entreprise.setEquipment(getEquipementsParDefaut()); // Ajouter équipements par défaut

                    // Mettre à jour Firestore avec les nouveaux équipements
                    entrepriseService.mettreAJourEntreprise(id, entreprise, success -> {
                        Log.d("getData", "Équipements ajoutés à l'entreprise !");
                    }, e -> Log.e("getData", "Erreur lors de la mise à jour de l'entreprise: " + e.getMessage()));
                }

                // Mise à jour de l'affichage
                equipments.clear();
                equipments.addAll(entreprise.getEquipment());
                runOnUiThread(() -> adapter.notifyDataSetChanged());

            } else {
                // Si l'entreprise n'existe pas, la créer
                Log.d("getData", "Entreprise non trouvée, création en cours...");
                creerNouvelleEntreprise();
            }
        }, e -> {
            Log.e("getData", "Erreur Firestore: " + e.getMessage());

            if (e.getMessage().contains("Entreprise non trouvée")) {
                creerNouvelleEntreprise();
            } else {
                Toast.makeText(getApplicationContext(), "Erreur de récupération des données", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void creerNouvelleEntreprise() {
        Entreprise nouvelleEntreprise = new Entreprise();
        nouvelleEntreprise.setIdEntreprise(id);
        nouvelleEntreprise.setEquipment(getEquipementsParDefaut()); // Ajoute équipements par défaut

        entrepriseService.ajouterEntreprisePersonnele(nouvelleEntreprise, success -> {
            Log.d("getData", "Nouvelle entreprise créée avec succès !");
            Toast.makeText(getApplicationContext(), "Nouvelle entreprise créée", Toast.LENGTH_SHORT).show();

            // Mise à jour de l'affichage
            equipments.clear();
            equipments.addAll(nouvelleEntreprise.getEquipment());
            runOnUiThread(() -> adapter.notifyDataSetChanged());

        }, e -> {
            Log.e("getData", "Erreur lors de la création de l'entreprise: " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Erreur création entreprise", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onAugmenter(Equipment item, Button btn, TextView t) {
        btn.setEnabled(false); // Désactiver le bouton

        if (id.equals(name)) {
            item.setQte(item.getQte() + 1);

            for (Equipment e : equipments) {
                if (e.getName().equals(item.getName())) {
                    e.setQte(item.getQte());
                    break;
                }
            }

            entrepriseService.mettreAJourEntreprise(id, entrepriseUtilise, succ -> {
                t.setText("Quantity : " + item.getQte());
                btn.setEnabled(true); // Réactiver le bouton après l'opération
            }, err -> {
                btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
            });
        } else {
            entrepriseService.getEntrepriseById(name, monEntreprise -> {
                if (monEntreprise != null && monEntreprise.getEquipment() != null) {
                    for (Equipment e : monEntreprise.getEquipment()) {
                        if (e.getName().equals(item.getName())) {
                            if (e.getQte() > 0) {
                                e.setQte(e.getQte() - 1);
                                item.setQte(item.getQte() + 1);

                                entrepriseService.mettreAJourEntreprise(name, monEntreprise, succ -> {
                                    entrepriseService.mettreAJourEntreprise(id, entrepriseUtilise, success -> {
                                        t.setText("Quantity : " + item.getQte());
                                        btn.setEnabled(true); // Réactiver le bouton après l'opération
                                    }, error -> {
                                        btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
                                    });
                                }, err -> {
                                    btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Stock insuffisant pour " + item.getName(), Toast.LENGTH_SHORT).show();
                                btn.setEnabled(true); // Réactiver le bouton si l'opération échoue
                            }
                            break;
                        }
                    }
                }
            }, err -> {
                btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
            });
        }
    }

    @Override
    public void onDiminuer(Equipment item, Button btn, TextView t) {
        btn.setEnabled(false); // Désactiver le bouton

        if (item.getQte() > 0) {
            item.setQte(item.getQte() - 1);

            if (id.equals(name)) {
                for (Equipment e : equipments) {
                    if (e.getName().equals(item.getName())) {
                        e.setQte(item.getQte());
                        break;
                    }
                }

                entrepriseService.mettreAJourEntreprise(id, entrepriseUtilise, succ -> {
                    t.setText("Quantity : " + item.getQte());
                    btn.setEnabled(true); // Réactiver le bouton après l'opération
                }, err -> {
                    btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
                });
            } else {
                entrepriseService.getEntrepriseById(name, monEntreprise -> {
                    if (monEntreprise != null && monEntreprise.getEquipment() != null) {
                        for (Equipment e : monEntreprise.getEquipment()) {
                            if (e.getName().equals(item.getName())) {
                                e.setQte(e.getQte() + 1);
                                entrepriseService.mettreAJourEntreprise(name, monEntreprise, succ -> {
                                    entrepriseService.mettreAJourEntreprise(id, entrepriseUtilise, success -> {
                                        t.setText("Quantity : " + item.getQte());
                                        btn.setEnabled(true); // Réactiver le bouton après l'opération
                                    }, error -> {
                                        btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
                                    });
                                }, err -> {
                                    btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
                                });
                                break;
                            }
                        }
                    }
                }, err -> {
                    btn.setEnabled(true); // Réactiver le bouton en cas d'erreur
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "Impossible de diminuer : quantité déjà à 0", Toast.LENGTH_SHORT).show();
            btn.setEnabled(true); // Réactiver le bouton si l'opération échoue
        }
    }

}
