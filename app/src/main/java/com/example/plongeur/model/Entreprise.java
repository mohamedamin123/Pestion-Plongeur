package com.example.plongeur.model;

public class Entreprise {

    private Integer idEntreprise;
    private String nom;
    private String telephone;
    private int nbrPlongeur;


    public Entreprise(){

    }
    public Entreprise(String nom, String telephone, int nbrPlongeur) {
        this.nom = nom;
        this.telephone = telephone;
        this.nbrPlongeur = nbrPlongeur;
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
