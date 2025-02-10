package com.example.plongeur.view.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plongeur.R;
import com.example.plongeur.databinding.CustomItemEntrepriseBinding;
import com.example.plongeur.databinding.CustomItemUserBinding;
import com.example.plongeur.model.Entreprise;
import com.example.plongeur.model.User;
import com.example.plongeur.view.entreprise.EntrepriseAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private static final int REQUEST_CALL_PHONE = 1;
    private Context context;
    private List<User> userList;
    private OnItemClickListener itemClickListener;

    public UserAdapter(List<User> entrepriseList, Context context, OnItemClickListener itemClickListener) {
        this.context = context;
        this.userList = entrepriseList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.binding.tvEmailUser.setText(user.getEmail());
        holder.binding.tvRole.setText(user.getRole());


        holder.itemView.setOnClickListener(e->itemClickListener.onItemClick(user));


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateList(List<User> newClients) {
        this.userList = new ArrayList<>(newClients);
        notifyDataSetChanged();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        CustomItemUserBinding binding;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemUserBinding.bind(itemView);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(User item);
    }
}
