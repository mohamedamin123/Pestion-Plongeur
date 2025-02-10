//package com.example.plongeur.controller;
//
//import android.app.Application;
//import android.os.Handler;
//import android.os.Looper;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.example.plongeur.model.Equipment;
//import com.example.plongeur.repository.EquipmentRepository;
//import com.example.plongeur.service.EquipmentService;
//
//import java.util.List;
//
//public class EquipementController extends AndroidViewModel {
//
//    EquipmentRepository repositiry;
//    public EquipementController(@NonNull Application application) {
//        super(application);
//        repositiry=new EquipmentRepository(application);
//    }
//
//    public void insert(Equipment... Equipments)
//    {
//        repositiry.insert(Equipments);
//    }
//    public  void update(Equipment... Equipments)
//    {
//        repositiry.update(Equipments);
//    }
//    public void delete(Equipment... Equipments)
//    {
//        repositiry.delete(Equipments);
//    }
//
//    public void deleteById(Integer id)
//    {
//        repositiry.deleteById(id);
//    }
//
//    public LiveData<Equipment> findById(Integer id)
//    {
//        return repositiry.findById(id);
//    }
//
//    public LiveData<List<Equipment>> findAll()
//    {
//        return  repositiry.findAll();
//    }
//
//    public LiveData<List<Equipment>> findByIdEntreprise(int id)
//    {
//        return  repositiry.findByIdEntreprise(id);
//    }
//
//    public LiveData<List<Equipment>> findStockPersonnel() {
//        return repositiry.getStockPersonnel();
//    }
//
//
//
//
//
//}
