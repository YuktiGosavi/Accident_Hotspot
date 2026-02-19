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

import java.util.ArrayList;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {

    ArrayList<CallModel> list;
    Context context;

    public CallAdapter(ArrayList<CallModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_call, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CallModel model = list.get(position);

        holder.txtName.setText(model.getName());
        holder.txtCallTime.setText(model.getTime());
        holder.imgCallType.setImageResource(model.getCallTypeIcon());

        holder.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + model.getNumber()));
            context.startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
            // You can open call detail screen here
            // For now showing toast
            android.widget.Toast.makeText(context, "Opening " + model.getName() + "'s call details", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfile, imgCallType, btnCall;
        TextView txtName, txtCallTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.imgProfile);
            imgCallType = itemView.findViewById(R.id.imgCallType);
            btnCall = itemView.findViewById(R.id.btnCall);
            txtName = itemView.findViewById(R.id.txtName);
            txtCallTime = itemView.findViewById(R.id.txtCallTime);
        }
    }
}

