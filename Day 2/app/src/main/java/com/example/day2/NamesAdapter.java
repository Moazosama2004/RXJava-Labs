package com.example.day2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.NamesViewHolder> {

    private ArrayList<String> namesList = new ArrayList<>();

    public void setNamesList(ArrayList<String> namesList){
        this.namesList = namesList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NamesAdapter.NamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NamesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.name_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull NamesAdapter.NamesViewHolder holder, int position) {
        holder.nameView.setText(namesList.get(position));
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    public class NamesViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        public NamesViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_txt);
        }
    }
}
