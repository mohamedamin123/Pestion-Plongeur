package com.example.plongeur.service;

import com.example.plongeur.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String COLLECTION_NAME = "users";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Ajouter un équipement à Firestore.
     * Si le document n'existe pas, il est ajouté.
     */
    public void ajouterUser(User User,
                                 OnSuccessListener<DocumentReference> onSuccessListener,
                                 OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .add(User)
                .addOnSuccessListener(documentReference -> {
                    // Récupérer l'ID généré par Firestore
                    String documentId = documentReference.getId();

                    // Assigner l'ID à l'équipement
                    User.setIdUser(documentId);

                    // Mettre à jour Firestore avec l'ID correct
                    db.collection(COLLECTION_NAME)
                            .document(documentId)
                            .set(User)
                            .addOnSuccessListener(aVoid -> onSuccessListener.onSuccess(documentReference))
                            .addOnFailureListener(onFailureListener);
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Lire un équipement par son ID.
     */
    public void getUserById(String idUser,
                                 OnSuccessListener<User> onSuccessListener,
                                 OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idUser)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User User = documentSnapshot.toObject(User.class);
                        onSuccessListener.onSuccess(User);
                    } else {
                        onFailureListener.onFailure(new Exception("Équipement non trouvé"));
                    }
                })
                .addOnFailureListener(onFailureListener);
    }




    /**
     * Lire tous les équipements.
     */
    public void getAllUsers(OnSuccessListener<List<User>> onSuccessListener,
                                 OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> Users = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        User User = document.toObject(User.class);
                        User.setIdUser(document.getId()); // Assigner l'ID Firestore
                        Users.add(User);
                    }
                    onSuccessListener.onSuccess(Users);
                })
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Mettre à jour un équipement.
     */
    public void mettreAJourUser(String idUser,
                                     User User,
                                     OnSuccessListener<Void> onSuccessListener,
                                     OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idUser)
                .set(User)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * Supprimer un équipement.
     */
    public void supprimerUser(String idUser,
                                   OnSuccessListener<Void> onSuccessListener,
                                   OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).document(idUser)
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }


    public void findByEmail(String email,
                            OnSuccessListener<User> onSuccessListener,
                            OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME)
                .whereEqualTo("email", email) // assuming "email" is the field name in the User document
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        User user = documentSnapshot.toObject(User.class);
                        user.setIdUser(documentSnapshot.getId()); // Set the Firestore document ID
                        onSuccessListener.onSuccess(user);
                    } else {
                        onFailureListener.onFailure(new Exception("Utilisateur non trouvé"));
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

}
