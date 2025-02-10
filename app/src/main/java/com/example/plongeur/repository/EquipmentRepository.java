//package com.example.plongeur.repository;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//
//import com.example.plongeur.DAO.EquipmentDAO;
//import com.example.plongeur.DAO.EquipmentDAO;
//import com.example.plongeur.R;
//import com.example.plongeur.config.MyRoomDataBase;
//import com.example.plongeur.model.Equipment;
//import com.example.plongeur.model.Equipment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class EquipmentRepository {
//
//
//    EquipmentDAO dao;
//
//
//    public EquipmentRepository(Application application) {
//        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
//        dao= db.equipmentDAO();
//    }
//
//
//    public void insert(Equipment... Equipmentss)
//    {
//        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                dao.insert(Equipmentss);
//            }
//        });
//    }
//    public  void update(Equipment... Equipmentss)
//    {
//        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                dao.update(Equipmentss);
//            }
//        });
//    }
//    public void delete(Equipment... Equipmentss)
//    {
//        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                dao.delete(Equipmentss);
//            }
//        });
//    }
//
//    public void deleteById(Integer id)
//    {
//        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                dao.deleteById(id);
//            }
//        });
//    }
//
//    public LiveData<Equipment> findById(Integer id)
//    {
//
//        return  dao.findById(id);
//
//    }
//
//    public LiveData<List<Equipment>> findAll()
//    {
//        return  dao.findAll();
//    }
//
//    public LiveData<List<Equipment>> findByIdEntreprise(int id)
//    {
//        return  dao.findByIdEntreprise(id);
//    }
//
//    public LiveData<List<Equipment>> getStockPersonnel() {
//        return dao.getStockPersonnel();
//    }
//
//}
