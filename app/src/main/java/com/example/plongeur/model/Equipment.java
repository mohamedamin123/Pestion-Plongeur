package com.example.plongeur.model;

import android.net.Uri;


public class Equipment {

    private Integer idEquipement;
    private String name;
    private int image;
    private String status;
    private int qte;

    public Equipment(){

    }

    public Equipment(String name, int image) {
        this.name = name;
        this.image=image;
        this.status = "Disponible";
    }

    public Equipment(Integer idEquipement, String name, int image) {
        this(name,image);
        this.idEquipement = idEquipement;
    }
    public Equipment(Equipment equipment) {
        this(equipment.getIdEquipement(), equipment.getName(), equipment.getImage());
        this.status = equipment.getStatus();
    }

    // Getters and Setters


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

    @Override
    public String toString() {
        return "Equipement{" +
                "idEquipement=" + idEquipement +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
