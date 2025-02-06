package com.example.plongeur.controller;

import com.example.plongeur.model.Equipment;
import com.example.plongeur.service.EquipmentService;

import java.util.List;

public class EquipementController {

    private EquipmentService equipmentService;

    public EquipementController(List<Equipment> equipments) {
        this.equipmentService = new EquipmentService(equipments);
    }

    /**
     * Retourne la liste complète des équipements
     */
    public List<Equipment> findAllEquipments() {
        return equipmentService.findAllEquipments();
    }
    /**
     * Recherche un équipement par ID
     */
    public Equipment findEquipmentById(int id) {
        return equipmentService.findEquipmentById(id);
    }
    /**
     * Recherche un équipement par nom
     */
    public Equipment findEquipmentByName(String name) {
        return equipmentService.findEquipmentByName(name);
    }
    /**
     * Ajoute un nouvel équipement à la liste
     */
    public void ajouterEquipment(Equipment equipement) {
        equipmentService.ajouterEquipment(equipement);
    }
    /**
     * Met à jour un équipement existant
     */
    public boolean updateEquipment(Equipment equipment) {
        return equipmentService.updateEquipment(equipment);
    }
    /**
     * Supprime un équipement par son ID
     */
    public boolean deleteEquipment(int id) {
        return equipmentService.deleteEquipment(id);
    }


}
