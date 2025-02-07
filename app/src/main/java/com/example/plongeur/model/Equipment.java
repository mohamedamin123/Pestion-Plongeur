package com.example.plongeur.model;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(
        tableName = "equipments",
        foreignKeys = @ForeignKey(
                entity = Entreprise.class,
                parentColumns = "idEntreprise",
                childColumns = "idEntreprise",
                onDelete = ForeignKey.CASCADE
        )
)
public class Equipment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEquipement")
    private Integer idEquipement;

    private String name;
    private int image;
    private String status;
    private int qte;

    @ColumnInfo(index = true) // Indice pour améliorer les performances des requêtes
    private Integer idEntreprise; // Permettre NULL

    public Equipment() {
    }

    public Equipment(String name, int image) {
        this.name = name;
        this.image = image;
        this.status = "Disponible";
        this.idEntreprise = null; // Aucune entreprise associée
    }

    public Equipment(String name, int image, Integer idEntreprise) {
        this(name, image);
        this.idEntreprise = idEntreprise; // Spécifier une entreprise si besoin
    }

    // Getters et Setters


    public Integer getIdEquipement() {
        return idEquipement;
    }

    public void setIdEquipement(Integer idEquipement) {
        this.idEquipement = idEquipement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Integer getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(Integer idEntreprise) {
        this.idEntreprise = idEntreprise;
    }
}
