package com.example.plongeur.service;

import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.Equipment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseService {
    private static final String COLLECTION_NAME = "entreprises";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Ajouter une entreprise à Firestore.
     * Si le document n'existe pas, il est ajouté.
     */
    public void ajouterEntreprise(Entreprise entreprise,
                                  OnSuccessListener<DocumentReference> onSuccessListener,
                                  OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .add(entreprise)
                .addOnSuccessListener(documentReference -> {
                    // Récupérer l'ID généré par Firestore
                    String documentId = documentReference.getId();

                    // Assigner l'ID à l'entreprise
                    entreprise.setIdEntreprise(documentId);

                    for (Equipment eqip:entreprise.getEquipment()) {
                        eqip.setIdEquipement(documentId);
                    }
                    // Mettre à jour Firestore avec l'ID correct
                    db.collection(COLLECTION_NAME)
                            .document(documentId)
                            .set(entreprise)
                            .addOnSuccessListener(aVoid -> onSuccessListener.onSuccess(documentReference))
                            .addOnFailureListener(onFailureListener);
                })
                .addOnFailureListener(onFailureListener);
    }


    public void ajouterEntreprisePersonnele(Entreprise entreprise,
                                            OnSuccessListener<Void> onSuccessListener,
                                            OnFailureListener onFailureListener) {
        // Définir l'ID du document Firestore
        String documentId = "MonEntreprise";

        // Assigner cet ID à l'objet entreprise
        entreprise.setIdEntreprise(documentId);

        for (Equipment eqip:entreprise.getEquipment()) {
            eqip.setIdEquipement(documentId);
        }

        // Ajouter l'entreprise avec l'ID personnalisé
        db.collection(COLLECTION_NAME)
                .document(documentId) // 🔥 Utilisation d'un ID personnalisé
                .set(entreprise)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }


    /**
     * Lire une entreprise par son ID.
     */
    public void getEntrepriseById(String idEntreprise,
                                  OnSuccessListener<Entreprise> onSuccessListener,
                                  OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idEntreprise)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Entreprise entreprise = documentSnapshot.toObject(Entreprise.class);
                        onSuccessListener.onSuccess(entreprise);
                    } else {
                        onFailureListener.onFailure(new Exception("Entreprise non trouvée"));
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Lire toutes les entreprises.
     */
    public void getAllEntreprises(OnSuccessListener<List<Entreprise>> onSuccessListener,
                                  OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Entreprise> entreprises = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Entreprise entreprise = document.toObject(Entreprise.class);
                        entreprise.setIdEntreprise(document.getId()); // Assigner l'ID Firestore
                        entreprises.add(entreprise);
                    }
                    onSuccessListener.onSuccess(entreprises);
                })
                .addOnFailureListener(onFailureListener);
    }

    public void getAllEntreprisesExcluding(String excludedId,
                                           OnSuccessListener<List<Entreprise>> onSuccessListener,
                                           OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Entreprise> entreprises = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (!document.getId().equals(excludedId)) { // Exclure l'entreprise avec l'ID spécifié
                            Entreprise entreprise = document.toObject(Entreprise.class);
                            entreprise.setIdEntreprise(document.getId()); // Assigner l'ID Firestore
                            entreprises.add(entreprise);
                        }
                    }
                    onSuccessListener.onSuccess(entreprises);
                })
                .addOnFailureListener(onFailureListener);
    }


    /**
     * Mettre à jour une entreprise.
     */
    public void mettreAJourEntreprise(String idEntreprise,
                                      Entreprise entreprise,
                                      OnSuccessListener<Void> onSuccessListener,
                                      OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idEntreprise)
                .set(entreprise)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Supprimer une entreprise.
     */
    public void supprimerEntreprise(String idEntreprise,
                                    OnSuccessListener<Void> onSuccessListener,
                                    OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idEntreprise)
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void findAllByIdEquipment(String idEntreprise,
                                     OnSuccessListener<List<Equipment>> onSuccessListener,
                                     OnFailureListener onFailureListener) {
        // Récupérer la sous-collection "equipments" de l'entreprise spécifique
        db.collection(COLLECTION_NAME)
                .document(idEntreprise)
                .collection("equipments") // Sous-collection
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Equipment> equipmentList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Equipment equipment = document.toObject(Equipment.class);
                        equipmentList.add(equipment);
                    }
                    onSuccessListener.onSuccess(equipmentList);
                })
                .addOnFailureListener(onFailureListener);
    }
}
