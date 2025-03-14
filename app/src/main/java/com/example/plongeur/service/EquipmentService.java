package com.example.plongeur.service;

import com.example.plongeur.model.Equipment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EquipmentService {
    private static final String COLLECTION_NAME = "equipments";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Ajouter un équipement à Firestore.
     * Si le document n'existe pas, il est ajouté.
     */
    public void ajouterEquipment(Equipment equipment,
                                 OnSuccessListener<DocumentReference> onSuccessListener,
                                 OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .add(equipment)
                .addOnSuccessListener(documentReference -> {
                    // Récupérer l'ID généré par Firestore
                    String documentId = documentReference.getId();

                    // Assigner l'ID à l'équipement
                    equipment.setIdEquipement(documentId);

                    // Mettre à jour Firestore avec l'ID correct
                    db.collection(COLLECTION_NAME)
                            .document(documentId)
                            .set(equipment)
                            .addOnSuccessListener(aVoid -> onSuccessListener.onSuccess(documentReference))
                            .addOnFailureListener(onFailureListener);
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Lire un équipement par son ID.
     */
    public void getEquipmentById(String idEquipment,
                                 OnSuccessListener<Equipment> onSuccessListener,
                                 OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idEquipment)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Equipment equipment = documentSnapshot.toObject(Equipment.class);
                        onSuccessListener.onSuccess(equipment);
                    } else {
                        onFailureListener.onFailure(new Exception("Équipement non trouvé"));
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Lire tous les équipements.
     */
    public void getAllEquipments(OnSuccessListener<List<Equipment>> onSuccessListener,
                                 OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Equipment> equipments = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Equipment equipment = document.toObject(Equipment.class);
                        equipment.setIdEquipement(document.getId()); // Assigner l'ID Firestore
                        equipments.add(equipment);
                    }
                    onSuccessListener.onSuccess(equipments);
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Mettre à jour un équipement.
     */
    public void mettreAJourEquipment(String idEquipment,
                                     Equipment equipment,
                                     OnSuccessListener<Void> onSuccessListener,
                                     OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idEquipment)
                .set(equipment)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Supprimer un équipement.
     */
    public void supprimerEquipment(String idEquipment,
                                   OnSuccessListener<Void> onSuccessListener,
                                   OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idEquipment)
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Trouver tous les équipements associés à une entreprise par son ID.
     */
    public void findAllByIdEntreprise(String idEntreprise,
                                      OnSuccessListener<List<Equipment>> onSuccessListener,
                                      OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .whereEqualTo("idEntreprise", idEntreprise) // Filtre sur l'ID de l'entreprise
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Equipment> equipments = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Equipment equipment = document.toObject(Equipment.class);
                        equipment.setIdEquipement(document.getId()); // Assigner l'ID Firestore
                        equipments.add(equipment);
                    }
                    onSuccessListener.onSuccess(equipments);
                })
                .addOnFailureListener(onFailureListener);
    }

    public void findAllMyStock(
                                      OnSuccessListener<List<Equipment>> onSuccessListener,
                                      OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .whereEqualTo("idEntreprise", null) // Filtre sur l'ID de l'entreprise
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Equipment> equipments = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Equipment equipment = document.toObject(Equipment.class);
                        equipment.setIdEquipement(document.getId()); // Assigner l'ID Firestore
                        equipments.add(equipment);
                    }
                    onSuccessListener.onSuccess(equipments);
                })
                .addOnFailureListener(onFailureListener);
    }

}
