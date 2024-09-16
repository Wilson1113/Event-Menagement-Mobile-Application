package com.fit2081.assignment3.categories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment3.R;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    ArrayList<Category> data = new ArrayList<>();

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryIdTv.setText(data.get(position).getCategoryId());
        holder.nameTv.setText(data.get(position).getName());
        holder.eventCountTv.setText(String.valueOf(data.get(position).getCount()));
        holder.isActiveTv.setText(data.get(position).isActive()?"Yes":"No");
        holder.cardView.setOnClickListener(v -> {
            String location = data.get(position).getEventLocation();
            Context context = v.getContext();
            Intent intent = new Intent(context, GoogleMapActivity.class);
            intent.putExtra("location", location);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryIdTv;
        public TextView nameTv;
        public TextView eventCountTv;
        public TextView isActiveTv;

        public View cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            categoryIdTv = itemView.findViewById(R.id.categoryId);
            nameTv = itemView.findViewById(R.id.categoryName);
            eventCountTv = itemView.findViewById(R.id.categoryEventCount);
            isActiveTv = itemView.findViewById(R.id.categoryIsActive);
        }
    }
}