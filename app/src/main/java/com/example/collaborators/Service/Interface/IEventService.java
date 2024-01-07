package com.example.collaborators.Service.Interface;



import com.example.collaborators.model.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IEventService {
    @GET("api/List")
    Call<List<Event>> getEvents();
}
