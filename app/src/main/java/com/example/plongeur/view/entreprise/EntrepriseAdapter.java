package com.example.plongeur.view.entreprise;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plongeur.R;
import com.example.plongeur.databinding.CustomItemEntrepriseBinding;
import com.example.plongeur.databinding.CustomItemEquipmentBinding;
import com.example.plongeur.model.Entreprise;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseAdapter extends RecyclerView.Adapter<EntrepriseAdapter.EntrepriseViewHolder> {

    private static final int REQUEST_CALL_PHONE = 1;
    private Context context;
    private List<Entreprise> entrepriseList;
    private OnItemClickListener itemClickListener;

    public EntrepriseAdapter(List<Entreprise> entrepriseList,Context context,OnItemClickListener itemClickListener) {
        this.context = context;
        this.entrepriseList = entrepriseList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public EntrepriseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_entreprise, parent, false);
        return new EntrepriseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntrepriseViewHolder holder, int position) {
        Entreprise entreprise = entrepriseList.get(position);
        holder.binding.entrepriseName.setText(entreprise.getNom());
        holder.binding.tvTel.setText(entreprise.getTelephone());
        holder.binding.plongeurQuantite.setText(MessageFormat.format("Nbr plongeurs : {0}", entreprise.getNbrPlongeur()));

        // Gestion des boutons d'augmentation et de diminution
        holder.binding.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = entreprise.getNbrPlongeur();
            entreprise.setNbrPlongeur(currentQuantity + 1);
            holder.binding.plongeurQuantite.setText(MessageFormat.format("Nbr plongeurs : {0}", entreprise.getNbrPlongeur()));
        });

        holder.binding.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = entreprise.getNbrPlongeur();
            if (currentQuantity > 0) {
                entreprise.setNbrPlongeur(currentQuantity - 1);
                holder.binding.plongeurQuantite.setText(MessageFormat.format("Nbr plongeurs : {0}", entreprise.getNbrPlongeur()));
            }
        });
        holder.binding.imgPhone.setOnClickListener(v->{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + entreprise.getTelephone()));

            // Check for permission
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                return;
            }
            context.startActivity(callIntent);
        });
        holder.itemView.setOnClickListener(e->itemClickListener.onItemClick(entreprise));


    }

    @Override
    public int getItemCount() {
        return entrepriseList.size();
    }

    public void updateList(List<Entreprise> newClients) {
        this.entrepriseList = new ArrayList<>(newClients);
        notifyDataSetChanged();
    }


    public static class EntrepriseViewHolder extends RecyclerView.ViewHolder {
        CustomItemEntrepriseBinding binding;


        public EntrepriseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemEntrepriseBinding.bind(itemView);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Entreprise item);
    }
}
