package com.fit2081.assignment3.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.fit2081.assignment3.categories.Category;
import com.fit2081.assignment3.events.Event;

import java.util.List;

@Dao
public interface DAO {

    @Query("select * from categories")
    LiveData<List<Category>> getAllCategories();

    @Insert
    void addCategories(Category category);

    @Query("update categories set count = count + 1 where categoryId = :categoryId")
    void addCount(String categoryId);

    @Query("update categories set count = count - 1 where categoryId = :categoryId")
    void minusCount(String categoryId);

    @Query("DELETE FROM categories")
    void deleteAllCategories();

    @Query("select * from events")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void addEvents(Event event);

    @Query("DELETE FROM events")
    void deleteAllEvents();

    @Query("delete from events where eventId = :eventId")
    void deleteEventById(String eventId);
}
