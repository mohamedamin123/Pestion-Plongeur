package com.example.plongeur.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.plongeur.model.User;

public class UserShared {

    private static final String PREF_NAME = "UserPreferences";
    private static final String KEY_ID = "idUser";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE = "role";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserShared(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Enregistrer les informations utilisateur
    public void saveUser(String id,String email, String password, String role) {
        editor.putString(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }
    public String getId() {
        return sharedPreferences.getString(KEY_ID, null);
    }

    // Récupérer l'email de l'utilisateur
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    // Récupérer le mot de passe de l'utilisateur
    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    // Récupérer le rôle de l'utilisateur
    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, null);
    }

    // Supprimer les informations utilisateur
    public void clearUser() {
        editor.remove(KEY_ID);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_ROLE);
        editor.apply();
    }



    // Vérifier si un utilisateur est déjà enregistré
    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(KEY_EMAIL) && sharedPreferences.contains(KEY_PASSWORD);
    }
}
