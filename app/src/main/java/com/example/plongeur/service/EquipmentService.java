package com.example.plongeur.service;

import com.example.plongeur.model.Equipment;
import com.example.plongeur.repository.EquipmentRepository;

import java.util.List;

public class EquipmentService {

    private EquipmentRepository equipmentRepository;

    public EquipmentService(List<Equipment> equipments) {

        this.equipmentRepository = new EquipmentRepository(equipments);
    }
    /**
     * Retourne la liste complète des équipements
     */
    public List<Equipment> findAllEquipments() {
        return equipmentRepository.findAllEquipments();
    }
    /**
     * Recherche un équipement par ID
     */
    public Equipment findEquipmentById(int id) {
        return equipmentRepository.findEquipmentById(id);
    }
    /**
     * Recherche un équipement par nom
     */
    public Equipment findEquipmentByName(String name) {
        return equipmentRepository.findEquipmentByNom(name);
    }
    /**
     * Ajoute un nouvel équipement à la liste
     */
    public void ajouterEquipment(Equipment equipement) {
         equipmentRepository.ajouterEquipment(equipement);
    }
    /**
     * Met à jour un équipement existant
     */
    public boolean updateEquipment(Equipment equipment) {
        return equipmentRepository.updateEquipment(equipment);
    }
    /**
     * Supprime un équipement par son ID
     */
    public boolean deleteEquipment(int id) {
        return equipmentRepository.deleteEquipment(id);
    }

}
