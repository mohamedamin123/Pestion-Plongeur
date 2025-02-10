//package com.example.plongeur.DAO;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.example.plongeur.model.Equipment;
//
//import java.util.List;
//
//@Dao
//public interface EquipmentDAO {
//
//    @Insert
//    void insert(Equipment... equipment);
//    @Update
//    void update(Equipment... equipment);
//    @Delete
//    void delete(Equipment... equipment);
//
//
//
//    @Query("delete from equipments where idEquipement=:id")
//    void deleteById(Integer id);
//
//    @Query("select * from equipments where idEquipement=:id")
//    LiveData<Equipment> findById(Integer id);
//
//    @Query("select * from equipments")
//    LiveData<List<Equipment>> findAll();
//
//    @Query("select * from equipments where idEntreprise=:id")
//    LiveData<List<Equipment>> findByIdEntreprise(Integer id);
//
//    @Query("SELECT * FROM equipments WHERE idEntreprise IS NULL OR idEntreprise = 0")
//    LiveData<List<Equipment>> getStockPersonnel();
//
//}
