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

        // Load Call List
        list = new ArrayList<>();
        loadCallLogs();

        adapter = new CallAdapter(list, getContext());
        recyclerCalls.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCalls.setAdapter(adapter);

        return view;
    }

    private void loadCallLogs() {

        list.add(new CallModel("Rahul Sharma", "Today, 9:45 AM", R.drawable.ic_call_received, "9876543210"));
        list.add(new CallModel("Neha", "Yesterday, 7:10 PM", R.drawable.ic_call_made, "9876501234"));
        list.add(new CallModel("Amit Kumar", "Yesterday, 5:30 PM", R.drawable.ic_call_missed, "9988776655"));
        list.add(new CallModel("Priya", "2 Feb, 11:00 AM", R.drawable.ic_call_received, "8877665544"));

    }
}
