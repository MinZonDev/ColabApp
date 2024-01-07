package com.example.collaborators.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.collaborators.Adapter.ListUpComingAdapter;
import com.example.collaborators.R;
import com.example.collaborators.Service.Interface.IEventService;
import com.example.collaborators.Service.RetrofitInstance;
import com.example.collaborators.model.Event;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUpcomingActivity extends AppCompatActivity {

    private Button btnBack;

    private RecyclerView recyclerView;
    private ListUpComingAdapter listUpComingAdapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_upcoming);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        fetchProducts();
        listUpComingAdapter = new ListUpComingAdapter(this, eventList);
        recyclerView.setAdapter(listUpComingAdapter);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void fetchProducts() {
        Call<List<Event>> call = RetrofitInstance.getRetrofitInstance().create(IEventService.class).getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    eventList.addAll(response.body());
                    listUpComingAdapter.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "Total products: " + eventList.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
// Handle the failure, Handle the failure appropriately based on your application's requirements
                Log.e("Network Request", "Error", t);
            }
        });
    }
}