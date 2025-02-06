package com.example.plongeur.service;

import com.example.plongeur.model.Entreprise;
import com.example.plongeur.repository.EntrepriseRepository;

import java.util.List;

public class EntrepriseService {

    private EntrepriseRepository entrepriseRepository;

    public EntrepriseService(List<Entreprise> Entreprises) {

        this.entrepriseRepository = new EntrepriseRepository(Entreprises);
    }
    /**
     * Retourne la liste complète des équipements
     */
    public List<Entreprise> findAllEntreprises() {
        return entrepriseRepository.findAllEntreprises();
    }
    /**
     * Recherche un équipement par ID
     */
    public Entreprise findEntrepriseById(int id) {
        return entrepriseRepository.findEntrepriseById(id);
    }
    /**
     * Recherche un équipement par nom
     */
    public Entreprise findEntrepriseByName(String name) {
        return entrepriseRepository.findEntrepriseByNom(name);
    }
    /**
     * Ajoute un nouvel équipement à la liste
     */
    public void ajouterEntreprise(Entreprise equipement) {
        entrepriseRepository.ajouterEntreprise(equipement);
    }
    /**
     * Met à jour un équipement existant
     */
    public boolean updateEntreprise(Entreprise Entreprise) {
        return entrepriseRepository.updateEntreprise(Entreprise);
    }
    /**
     * Supprime un équipement par son ID
     */
    public boolean deleteEntreprise(int id) {
        return entrepriseRepository.deleteEntreprise(id);
    }

}
