package com.fit2081.assignment3.events;


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

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

    ArrayList<Event> data = new ArrayList<Event>();

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String eventId = "Id: " + data.get(position).getEventId();
        String categoryId = "Category Id: " + data.get(position).getCategoryId();
        String name = "Name: " + data.get(position).getName();
        String ticketsAvailable = "Tickets: " + data.get(position).getTicketsAvailable();
        String isActive = data.get(position).isActive() ? "Active" : "Not Active";
        holder.eventIdTv.setText(eventId);
        holder.categoryIdTv.setText(categoryId);
        holder.nameTv.setText(name);
        holder.ticketsAvailableTv.setText(ticketsAvailable);
        holder.isActiveTv.setText(isActive);

        holder.cardView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("url", data.get(position).getName());
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
        public TextView ticketsAvailableTv;
        public TextView isActiveTv;
        public TextView eventIdTv;
        public View cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            eventIdTv = itemView.findViewById(R.id.a);
            categoryIdTv = itemView.findViewById(R.id.b);
            nameTv = itemView.findViewById(R.id.c);
            ticketsAvailableTv = itemView.findViewById(R.id.d);
            isActiveTv = itemView.findViewById(R.id.e);
        }
    }
}