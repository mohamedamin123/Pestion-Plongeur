package com.example.plongeur.model;


import com.example.plongeur.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Entreprise {

    private String idEntreprise;
    private String nom;
    private String telephone;
    private int nbrPlongeur;
    private List<Equipment> equipment;


    public Entreprise(){
        this.nom="Mon Entreprise";
        this.telephone="";
        this.nbrPlongeur=0;
        this.equipment=new ArrayList<>();
        this.equipment.addAll(Arrays.asList(
                new Equipment("Gilet", R.drawable.gilet),
                new Equipment("Détendeur", R.drawable.detendeur),
                new Equipment("Masque", R.drawable.mask),
                new Equipment("Palmes", R.drawable.palm),
                new Equipment("Bottes", R.drawable.boot),
                new Equipment("Bouteille", R.drawable.bouteille),
                new Equipment("Combinaison", R.drawable.combinaison),
                new Equipment("Ceinture", R.drawable.ceinture)
        ));
    }
    public Entreprise(String nom, String telephone, int nbrPlongeur) {
        this.nom = nom;
        this.telephone = telephone;
        this.nbrPlongeur = nbrPlongeur;
        this.equipment = new ArrayList<>();

        this.equipment.addAll(Arrays.asList(
                new Equipment("Gilet", R.drawable.gilet),
                new Equipment("Détendeur", R.drawable.detendeur),
                new Equipment("Masque", R.drawable.mask),
                new Equipment("Palmes", R.drawable.palm),
                new Equipment("Bottes", R.drawable.boot),
                new Equipment("Bouteille", R.drawable.bouteille),
                new Equipment("Combinaison", R.drawable.combinaison),
                new Equipment("Ceinture", R.drawable.ceinture)
        ));
    }
    public Entreprise(String idEntreprise,String nom, String telephone, int nbrPlongeur) {
        this(nom,telephone,nbrPlongeur);
        this.idEntreprise = idEntreprise;
    }
    public Entreprise(Entreprise entreprise) {
        this(entreprise.getIdEntreprise(),entreprise.getNom(), entreprise.getTelephone(), entreprise.getNbrPlongeur());
    }

    public String getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(String idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getNbrPlongeur() {
        return nbrPlongeur;
    }

    public void setNbrPlongeur(int nbrPlongeur) {
        this.nbrPlongeur = nbrPlongeur;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "idEntreprise=" + idEntreprise +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", nbrPlongeur='" + nbrPlongeur + '\'' +
                '}';
    }
}
