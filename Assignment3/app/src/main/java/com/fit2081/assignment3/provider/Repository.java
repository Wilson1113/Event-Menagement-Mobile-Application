package com.fit2081.assignment3.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.assignment3.categories.Category;
import com.fit2081.assignment3.events.Event;

import java.util.List;

public class Repository {

    // private class variable to hold reference to DAO
    private DAO DAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Category>> allCategoriesLiveData;
    private LiveData<List<Event>> allEventsLiveData;

    // constructor to initialise the repository class
    Repository(Application application) {
        // get reference/instance of the database
        Database db = Database.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        DAO = db._DAO();

        // once the class is initialised get all the items in the form of LiveData
        allCategoriesLiveData = DAO.getAllCategories();

        allEventsLiveData = DAO.getAllEvents();


    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<Category>> getAllCategories() {
        return allCategoriesLiveData;
    }
    /**
     * Repository method to insert one single item
     * @param category object containing details of new Item to be inserted
     */
    void insert(Category category) {
        Database.databaseWriteExecutor.execute(() -> DAO.addCategories(category));
    }

    void addCount(String categoryId) {
        Database.databaseWriteExecutor.execute(() -> DAO.addCount(categoryId));
    }

    void minusCount(String categoryId) {
        Database.databaseWriteExecutor.execute(() -> DAO.minusCount(categoryId));
    }

    /**
     * Repository method to delete all records
     */
    void deleteAllCategories() {
        Database.databaseWriteExecutor.execute(() -> DAO.deleteAllCategories());
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }


    /**
     * Repository method to insert one single item
     * @param event object containing details of new Item to be inserted
     */
    void insert(Event event) {
        Database.databaseWriteExecutor.execute(() -> DAO.addEvents(event));
    }

    /**
     * Repository method to delete all records
     */
    void deleteAllEvents() {
        Database.databaseWriteExecutor.execute(() -> DAO.deleteAllEvents());
    }

    void deleteEventById(String eventId) {
        Database.databaseWriteExecutor.execute(() -> DAO.deleteEventById(eventId));
    }
}
