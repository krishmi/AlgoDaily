package com.example.algodaily;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class AlgorithmsAdapter extends RecyclerView.Adapter<AlgorithmsAdapter.ViewHolder>
{
    private final AlgoDatabaseHelper dbHelper;
    public AlgorithmsAdapter(AlgoDatabaseHelper db)
    {
        dbHelper = db;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView algoName;
        private final TextView algoLevel;
        private final TextView algoStatus;

        public ViewHolder(View view)
        {
            super(view);
            algoName = view.findViewById(R.id.algoname);
            algoLevel = view.findViewById(R.id.algolevel);
            algoStatus = view.findViewById(R.id.algostatus);
        }

        public  TextView getAlgoName() {
            return algoName;
        }

        public TextView getAlgoLevel() {
            return algoLevel;
        }

        public TextView getAlgoStatus() { return algoStatus; }
    }

    @NonNull
    @NotNull
    @Override
    public AlgorithmsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.algoitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlgorithmsAdapter.ViewHolder holder, int position)
    {
        dbHelper.setAlgoNameAndLevel(holder.getAlgoLevel(), holder.getAlgoName(), holder.getAlgoStatus(), position);
    }

    @Override
    public int getItemCount()
    {
        return dbHelper.getNumberOfAlgosAtLevel("Easy")
                + dbHelper.getNumberOfAlgosAtLevel("Medium")
                + dbHelper.getNumberOfAlgosAtLevel("Hard");
    }
}
