package com.example.plongeur.controller;

import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.service.EntrepriseService;

import java.util.List;

public class EntrepriseController {

    private EntrepriseService entrepriseService;

    public EntrepriseController(List<Entreprise> Entreprises) {
        this.entrepriseService = new EntrepriseService(Entreprises);
    }

    /**
     * Retourne la liste complète des équipements
     */
    public List<Entreprise> findAllEntreprises() {
        return entrepriseService.findAllEntreprises();
    }
    /**
     * Recherche un équipement par ID
     */
    public Entreprise findEntrepriseById(int id) {
        return entrepriseService.findEntrepriseById(id);
    }
    /**
     * Recherche un équipement par nom
     */
    public Entreprise findEntrepriseByName(String name) {
        return entrepriseService.findEntrepriseByName(name);
    }
    /**
     * Ajoute un nouvel équipement à la liste
     */
    public void ajouterEntreprise(Entreprise equipement) {
        entrepriseService.ajouterEntreprise(equipement);
    }
    /**
     * Met à jour un équipement existant
     */
    public boolean updateEntreprise(Entreprise Entreprise) {
        return entrepriseService.updateEntreprise(Entreprise);
    }
    /**
     * Supprime un équipement par son ID
     */
    public boolean deleteEntreprise(int id) {
        return entrepriseService.deleteEntreprise(id);
    }


}
