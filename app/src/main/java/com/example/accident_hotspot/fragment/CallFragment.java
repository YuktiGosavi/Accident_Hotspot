package com.example.accident_hotspot.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import com.example.accident_hotspot.CallAdapter;
import com.example.accident_hotspot.CallModel;
import com.example.accident_hotspot.R;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CallFragment extends Fragment {

    RecyclerView recyclerCalls;
    ArrayList<CallModel> list;
    CallAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_call, container, false);

        recyclerCalls = view.findViewById(R.id.recyclerCalls);

        // Load Emergency Contacts
        list = new ArrayList<>();
        loadEmergencyContacts();

        adapter = new CallAdapter(list, getContext());
        recyclerCalls.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCalls.setAdapter(adapter);

        return view;
    }

    private void loadEmergencyContacts() {

        // ⭐ MAIN ACCIDENT & DISASTER EMERGENCIES
        list.add(new CallModel("Emergency Helpline (ERSS)",
                "", R.drawable.ic_call_received, "112"));
        list.add(new CallModel("Ambulance",
                "", R.drawable.ic_call_received, "102"));
        list.add(new CallModel("Disaster Management",
                "", R.drawable.ic_call_received, "108"));
        list.add(new CallModel("Road Accident Emergency",
                "", R.drawable.ic_call_received, "1073"));
        list.add(new CallModel("National Highway Accident Helpline",
                "", R.drawable.ic_call_received, "1033"));

        // ⭐ POLICE & SAFETY
        list.add(new CallModel("Police Control Room",
                "", R.drawable.ic_call_made, "100"));
        list.add(new CallModel("Traffic Police Helpline",
                "", R.drawable.ic_call_made, "103"));
        list.add(new CallModel("Traffic Control Room",
                "", R.drawable.ic_call_missed, "1099"));

        // ⭐ FIRE / GAS / CHEMICAL ACCIDENTS
        list.add(new CallModel("Fire Brigade",
                "", R.drawable.ic_call_made, "101"));
        list.add(new CallModel("Gas Leakage Emergency",
                "", R.drawable.ic_call_received, "1906"));
        list.add(new CallModel("Chemical Accident Helpline",
                "", R.drawable.ic_call_received, "18001805525"));

        // ⭐ MOTOR VEHICLE ACCIDENT SUPPORT
        list.add(new CallModel("Vehicle Accident Helpline",
                "", R.drawable.ic_call_received, "1969"));
        list.add(new CallModel("Air Ambulance Emergency",
                "", R.drawable.ic_call_received, "9540161344"));

        // ⭐ RAILWAY ACCIDENTS
        list.add(new CallModel("Railway Accident Emergency",
                "", R.drawable.ic_call_received, "1072"));
        list.add(new CallModel("Railway Enquiry & Reporting",
                "", R.drawable.ic_call_received, "139"));

        // ⭐ MEDICAL & POISON / BURN ACCIDENTS
        list.add(new CallModel("Red Cross Ambulance",
                "", R.drawable.ic_call_received, "1056"));
        list.add(new CallModel("Poison Information Center",
                "", R.drawable.ic_call_received, "18001805533"));
        list.add(new CallModel("Burn Emergency Helpline",
                "", R.drawable.ic_call_received, "1910"));

        // ⭐ CHILD / WOMEN RELATED EMERGENCIES
        list.add(new CallModel("Women Helpline",
                "", R.drawable.ic_call_made, "1091"));
        list.add(new CallModel("Child Emergency",
                "", R.drawable.ic_call_made, "1098"));

        // ⭐ OPTIONAL LOCAL / CUSTOM CONTACTS
        list.add(new CallModel("Nearest Hospital",
                "", R.drawable.ic_call_received, "9876543210"));
        list.add(new CallModel("Local Accident Response Team",
                "", R.drawable.ic_call_received, "1800180150"));
    }
}