package com.example.accident_hotspot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        // 📞 CALL
        holder.imgCall.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:" + contact.getPhone()));
            context.startActivity(i);
        });

        // 📩 SMS
        holder.imgSms.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("sms:" + contact.getPhone()));
            context.startActivity(i);
        });

        // ✏️ EDIT
        holder.imgEdit.setOnClickListener(v -> {
            Intent i = new Intent(context, AddEmergencyContactActivity.class);
            i.putExtra("name", contact.getName());
            i.putExtra("relation", contact.getRelation());
            i.putExtra("phone", contact.getPhone());
            context.startActivity(i);
        });

        // ❌ DELETE
        holder.imgDelete.setOnClickListener(v -> {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
            Toast.makeText(context, "Contact Deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtRelation, txtPhone;
        ImageView imgCall, imgSms, imgEdit, imgDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtRelation = itemView.findViewById(R.id.txtRelation);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgCall = itemView.findViewById(R.id.imgCall);
            imgSms = itemView.findViewById(R.id.imgSms);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
