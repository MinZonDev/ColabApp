package com.example.collaborators.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collaborators.Activites.DetailEventUpcomingActivity;
import com.example.collaborators.R;
import com.example.collaborators.model.Event;


import java.util.List;

public class ListUpComingAdapter extends RecyclerView.Adapter<ListUpComingAdapter.EventViewHolder> {

    private Context context;
    private List<Event> events;



    public ListUpComingAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.events = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listupcoming, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);

        holder.titleTextView.setText(event.getTitle());
//        holder.descriptionTextView.setText(event.getDescription());
        holder.locationTextView.setText(event.getLocation());
        holder.timeTextView.setText(event.getTime());

        // Use Glide to load the image from the URL
        Glide.with(context).load(event.getImage()).into(holder.imageView);


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailEventUpcomingActivity.class);
                intent.putExtra("eventID", event.getId());
                intent.putExtra("eventTitle", event.getTitle());
                intent.putExtra("eventImage", event.getImage());
                intent.putExtra("eventDescription", event.getDescription());
                intent.putExtra("eventLocation", event.getLocation());
                intent.putExtra("eventTime", event.getTime());
                // Add other data you want to pass

                context.startActivity(intent);
            }
        });

        holder.btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle registration button click here
                // You can use 'event' to get information about the event if needed
                // For example, you might want to show a registration form/activity

                Intent intent = new Intent(context, DetailEventUpcomingActivity.class);
                intent.putExtra("eventID", event.getId());
                intent.putExtra("eventTitle", event.getTitle());
                // Add other data you want to pass

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, descriptionTextView, locationTextView, timeTextView;
        private ImageView imageView;

        private Button btnRegistration;
        private RelativeLayout relativeLayout;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
//            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            imageView = itemView.findViewById(R.id.imageView);
            btnRegistration = itemView.findViewById(R.id.btnDangKy);

            relativeLayout = itemView.findViewById(R.id.layoutItem);


        }
    }

}
