package com.example.plongeur.config;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.plongeur.DAO.EntrepriseDAO;
import com.example.plongeur.DAO.EquipmentDAO;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.Equipment;
import com.example.plongeur.utils.DateConverteur;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Entreprise.class,  Equipment.class
}, version = 1, exportSchema = false)
@TypeConverters({DateConverteur.class})
public abstract class MyRoomDataBase extends RoomDatabase {
    private final static String DATABASE_NAME = "test_1.1";

    public abstract EquipmentDAO equipmentDAO();
    public abstract EntrepriseDAO entrepriseDAO();


    private static volatile MyRoomDataBase INSTANCE; //SINGLETON et volatile 1,2,3,4,5 t4aker exeple thread banque
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static MyRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDataBase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}