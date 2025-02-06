package com.example.plongeur.repository;

import com.example.plongeur.model.Entreprise;

import java.util.List;
import java.util.Objects;

public class EntrepriseRepository {

    private List<Entreprise> entreprises;

    public EntrepriseRepository(List<Entreprise> Entreprises ) {
        this.entreprises = Entreprises;
    }

    /**
     * Retourne la liste complète des équipements
     */
    public List<Entreprise> findAllEntreprises() {
        return entreprises;
    }

    /**
     * Recherche un équipement par ID
     */
    public Entreprise findEntrepriseById(int id) {
        for (Entreprise Entreprise : entreprises) {
            if (Entreprise.getIdEntreprise() == id) {
                return Entreprise;
            }
        }
        return null; // Si l'équipement n'existe pas
    }

    /**
     * Recherche un équipement par nom
     */
    public Entreprise findEntrepriseByNom(String nom) {
        for (Entreprise Entreprise : entreprises) {
            if (Entreprise.getNom().equalsIgnoreCase(nom)) {
                return Entreprise;
            }
        }
        return null; // Si l'équipement n'existe pas
    }

    /**
     * Ajoute un nouvel équipement à la liste
     */
    public void ajouterEntreprise(Entreprise Entreprise) {
        entreprises.add(Entreprise);
    }

    /**
     * Met à jour un équipement existant
     */
    public boolean updateEntreprise(Entreprise Entreprise) {
        for (int i = 0; i < entreprises.size(); i++) {
            if (Objects.equals(entreprises.get(i).getIdEntreprise(), Entreprise.getIdEntreprise())) {
                entreprises.set(i, Entreprise);
                return true;
            }
        }
        return false; // Équipement non trouvé
    }

    /**
     * Supprime un équipement par son ID
     */
    public boolean deleteEntreprise(int id) {
        return entreprises.removeIf(Entreprise -> Entreprise.getIdEntreprise() == id);
    }
}
