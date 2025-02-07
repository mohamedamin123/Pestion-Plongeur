package com.example.plongeur.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "entreprises")
public class Entreprise {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEntreprise")
    private Integer idEntreprise;
    private String nom;
    private String telephone;
    private int nbrPlongeur;
    @Ignore
    private List<Equipment> equipment;


    public Entreprise(){
        this.equipment=new ArrayList<>();
    }
    public Entreprise(String nom, String telephone, int nbrPlongeur) {
        this.nom = nom;
        this.telephone = telephone;
        this.nbrPlongeur = nbrPlongeur;
        this.equipment=new ArrayList<>();
    }

    public Entreprise(Integer idEntreprise,String nom, String telephone, int nbrPlongeur) {
        this(nom,telephone,nbrPlongeur);
        this.idEntreprise = idEntreprise;
    }
    public Entreprise(Entreprise entreprise) {
        this(entreprise.getIdEntreprise(),entreprise.getNom(), entreprise.getTelephone(), entreprise.getNbrPlongeur());
    }

    public Integer getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(Integer idEntreprise) {
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
