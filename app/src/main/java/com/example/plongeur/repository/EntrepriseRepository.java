//package com.example.plongeur.repository;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//
//import com.example.plongeur.DAO.EntrepriseDAO;
//import com.example.plongeur.config.MyRoomDataBase;
//import com.example.plongeur.model.Entreprise;
//
//import java.util.List;
//import java.util.Objects;
//
//public class EntrepriseRepository {
//
//
//    EntrepriseDAO dao;
//
//
//    public EntrepriseRepository(Application application) {
//        MyRoomDataBase db=MyRoomDataBase.getDatabase(application);
//        dao= db.entrepriseDAO();
//    }
//
//
//    public void insert(Entreprise... entreprisess)
//    {
//        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                dao.insert(entreprisess);
//            }
//        });
//    }
//    public  void update(Entreprise... entreprisess)
//    {
//        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                dao.update(entreprisess);
//            }
//        });
//    }
//    public void delete(Entreprise... entreprisess)
//    {
//        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                dao.delete(entreprisess);
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
//    public LiveData<Entreprise> findById(Integer id)
//    {
//
//        return  dao.findById(id);
//
//    }
//
//    public LiveData<List<Entreprise>> findAll()
//    {
//        return  dao.findAll();
//    }
//
//}
