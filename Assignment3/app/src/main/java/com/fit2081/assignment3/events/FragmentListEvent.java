package com.fit2081.assignment3.events;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment3.R;
import com.fit2081.assignment3.provider.ViewModel;

import java.util.ArrayList;

public class FragmentListEvent extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventRecyclerAdapter adapter;

    private ViewModel viewModel;


    public FragmentListEvent() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_event, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view2);

        layoutManager = new LinearLayoutManager(getContext());  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        adapter = new EventRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getAllEvents().observe(getViewLifecycleOwner(), newData -> {
            adapter.setData(new ArrayList<>(newData));
            adapter.notifyDataSetChanged();
        });


        return view;
    }
}