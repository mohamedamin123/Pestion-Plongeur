package com.example.plongeur.view.equipements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private OnItemClickListener listener;

    public EquipmentAdapter(List<Equipment> equipmentList, Context context,OnItemClickListener listener) {
        this.equipmentList = equipmentList;
        this.context = context;
        this.listener=listener;
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
        holder.binding.equipmentQuantite.setText("Quantité : " + equipment.getQte());
        holder.binding.equipmentImage.setImageResource(equipment.getImage());

        holder.binding.btnIncrease.setOnClickListener(v -> {

            listener.onAugmenter(equipment,holder.binding.btnIncrease,holder.binding.equipmentQuantite);
        });

        holder.binding.btnDecrease.setOnClickListener(v -> {

                listener.onDiminuer(equipment,holder.binding.btnDecrease,holder.binding.equipmentQuantite);

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

    public interface OnItemClickListener {
        void onAugmenter(Equipment item, Button btnDecrease, TextView t);
        void onDiminuer(Equipment item, Button btnDecrease,TextView t);
    }
}
