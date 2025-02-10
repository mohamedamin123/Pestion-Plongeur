//package com.example.plongeur.DAO;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.example.plongeur.model.Entreprise;
//
//import java.util.List;
//
//@Dao
//public interface EntrepriseDAO {
//
//    @Insert
//    void insert(Entreprise... Entreprise);
//    @Update
//    void update(Entreprise... Entreprise);
//    @Delete
//    void delete(Entreprise... Entreprise);
//
//
//
//    @Query("delete from Entreprises where idEntreprise=:id")
//    void deleteById(Integer id);
//
//    @Query("select * from Entreprises where idEntreprise=:id")
//    LiveData<Entreprise> findById(Integer id);
//
//    @Query("select * from Entreprises")
//    LiveData<List<Entreprise>> findAll();
//
//}
