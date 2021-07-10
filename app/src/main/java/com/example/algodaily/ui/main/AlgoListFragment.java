package com.example.algodaily.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.algodaily.AlgoDatabaseHelper;
import com.example.algodaily.AlgorithmsAdapter;
import com.example.algodaily.R;
import com.example.algodaily.databinding.FragmentMainBinding;

public class AlgoListFragment extends Fragment
{
    private final AlgoDatabaseHelper dbHelper;

    AlgoListFragment(AlgoDatabaseHelper db)
    {
        dbHelper = db;
    }

    public static AlgoListFragment newInstance(AlgoDatabaseHelper db) { return new AlgoListFragment(db); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView algoListRecyclerView = root.findViewById(R.id.algolist);
        algoListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        algoListRecyclerView.setAdapter(new AlgorithmsAdapter(dbHelper));
        return root;
    }
}