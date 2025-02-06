package com.example.plongeur.view.equipements;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.plongeur.R;
import com.example.plongeur.databinding.CustomItemEquipmentBinding;
import com.example.plongeur.model.Equipment;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {

    private List<Equipment> equipmentList;
    private Context context;

    public EquipmentAdapter(List<Equipment> equipmentList, Context context) {
        this.equipmentList = equipmentList;
        this.context = context;
    }

    public EquipmentAdapter(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @NonNull
    @Override
    public EquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_equipment, parent, false);
        return new EquipmentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EquipmentViewHolder holder, int position) {
        Equipment equipment = equipmentList.get(position);
        holder.binding.equipmentName.setText(equipment.getName());
        holder.binding.equipmentQuantite.setText("Quantite : " + equipment.getQte());
        holder.binding.equipmentImage.setImageResource(equipment.getImage());
        // Bouton "+" pour augmenter la quantité
        holder.binding.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = equipment.getQte();
            equipment.setQte(currentQuantity + 1);
            holder.binding.equipmentQuantite.setText("Quantite : " + equipment.getQte());
        });

        // Bouton "-" pour diminuer la quantité (sans aller en dessous de 0)
        holder.binding.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = equipment.getQte();
            if (currentQuantity > 0) {
                equipment.setQte(currentQuantity - 1);
                holder.binding.equipmentQuantite.setText("Quantite : " + equipment.getQte());
            }
        });

    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public static class EquipmentViewHolder extends RecyclerView.ViewHolder {
        CustomItemEquipmentBinding binding;


        public EquipmentViewHolder(View itemView) {
            super(itemView);
            binding = CustomItemEquipmentBinding.bind(itemView);

        }
    }
}
