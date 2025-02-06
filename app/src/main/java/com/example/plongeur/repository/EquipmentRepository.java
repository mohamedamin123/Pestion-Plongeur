package com.example.plongeur.repository;

import com.example.plongeur.R;
import com.example.plongeur.model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentRepository {

    private List<Equipment> equipments;

    public EquipmentRepository(List<Equipment> equipments ) {
        this.equipments = equipments;
    }

    /**
     * Retourne la liste complète des équipements
     */
    public List<Equipment> findAllEquipments() {
        return equipments;
    }

    /**
     * Recherche un équipement par ID
     */
    public Equipment findEquipmentById(int id) {
        for (Equipment equipment : equipments) {
            if (equipment.getIdEquipement() == id) {
                return equipment;
            }
        }
        return null; // Si l'équipement n'existe pas
    }

    /**
     * Recherche un équipement par nom
     */
    public Equipment findEquipmentByNom(String nom) {
        for (Equipment equipment : equipments) {
            if (equipment.getName().equalsIgnoreCase(nom)) {
                return equipment;
            }
        }
        return null; // Si l'équipement n'existe pas
    }

    /**
     * Ajoute un nouvel équipement à la liste
     */
    public void ajouterEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    /**
     * Met à jour un équipement existant
     */
    public boolean updateEquipment(Equipment equipment) {
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getIdEquipement() == equipment.getIdEquipement()) {
                equipments.set(i, equipment);
                return true;
            }
        }
        return false; // Équipement non trouvé
    }

    /**
     * Supprime un équipement par son ID
     */
    public boolean deleteEquipment(int id) {
        return equipments.removeIf(equipment -> equipment.getIdEquipement() == id);
    }
}
