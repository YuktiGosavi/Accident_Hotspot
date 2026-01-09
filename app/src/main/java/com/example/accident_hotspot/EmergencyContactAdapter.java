package com.example.accident_hotspot;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder> {

    Context context;
    List<EmergencyContact> list;

    public EmergencyContactAdapter(Context context, List<EmergencyContact> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.emergency_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmergencyContact contact = list.get(position);

        holder.txtName.setText(contact.getName());
        holder.txtRelation.setText(contact.getRelation());
        holder.txtPhone.setText(contact.getPhone());

        holder.txtAvatar.setText(
                contact.getName().substring(0,1).toUpperCase()
        );

        holder.btnEdit.setOnClickListener(v ->
                Toast.makeText(context, "Edit " + contact.getName(),
                        Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAvatar, txtName, txtRelation, txtPhone;
        ImageView btnEdit;

        ViewHolder(View itemView) {
            super(itemView);
            txtAvatar = itemView.findViewById(R.id.txtAvatar);
            txtName = itemView.findViewById(R.id.txtName);
            txtRelation = itemView.findViewById(R.id.txtRelation);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
