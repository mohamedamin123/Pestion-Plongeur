package com.example.plongeur.controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.repository.EntrepriseRepository;
import com.example.plongeur.service.EntrepriseService;

import java.util.List;

public class EntrepriseController extends AndroidViewModel {

    EntrepriseRepository repositiry;
    public EntrepriseController(@NonNull Application application) {
        super(application);
        repositiry=new EntrepriseRepository(application);
    }

    public void insert(Entreprise... Entreprises)
    {
        repositiry.insert(Entreprises);
    }
    public  void update(Entreprise... Entreprises)
    {
        repositiry.update(Entreprises);
    }
    public void delete(Entreprise... Entreprises)
    {
        repositiry.delete(Entreprises);
    }

    public void deleteById(Integer id)
    {
        repositiry.deleteById(id);
    }

    public LiveData<Entreprise> findById(Integer id)
    {
        return repositiry.findById(id);
    }

    public LiveData<List<Entreprise>> findAll()
    {
        return  repositiry.findAll();
    }



}
