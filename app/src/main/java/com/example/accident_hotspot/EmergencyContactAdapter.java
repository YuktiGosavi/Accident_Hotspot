package com.example.accident_hotspot;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmergencyContactAdapter
        extends RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder> {

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
                .inflate(R.layout.row_emergency_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmergencyContact contact = list.get(position);

        holder.txtName.setText(contact.getName());
        holder.txtRelation.setText(contact.getRelation());
        holder.txtPhone.setText(contact.getPhone());

        // ✅ IN-APP CALL
        holder.imgCall.setOnClickListener(v -> {
            Intent intent = new Intent(context, EmergenceActivity.class);
            intent.putExtra("name", contact.getName());
            intent.putExtra("number", contact.getPhone());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtRelation, txtPhone;
        ImageView imgCall;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtRelation = itemView.findViewById(R.id.txtRelation);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgCall = itemView.findViewById(R.id.imgCall);
        }
    }
}
