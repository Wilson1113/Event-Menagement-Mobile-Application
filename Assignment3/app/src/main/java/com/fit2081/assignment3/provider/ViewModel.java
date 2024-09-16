package com.fit2081.assignment3.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment3.categories.Category;
import com.fit2081.assignment3.events.Event;

import java.util.List;


/**
 * ViewModel class is used for pre-processing the data,
 * before passing it to the controllers (Activity or Fragments). ViewModel class should not hold
 * direct reference to database. ViewModel class relies on repository class, hence the database is
 * accessed using the Repository class.
 */
public class ViewModel extends AndroidViewModel {
    // reference to Repository
    private Repository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Category>> allCategoriesLiveData;
    private LiveData<List<Event>> allEventsLiveData;

    public ViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new Repository(application);

        // get all items by calling method defined in repository class
        allCategoriesLiveData = repository.getAllCategories();
        allEventsLiveData = repository.getAllEvents();
    }

    /**
     * ViewModel method to get all categories
     * @return LiveData of type List<Category>
     */
    public LiveData<List<Category>> getAllCategories() {
        return allCategoriesLiveData;
    }

    public void addCount(String categoryId) {
        repository.addCount(categoryId);
    }

    public void minusCount(String categoryId) {
        repository.minusCount(categoryId);
    }


    /**
     * ViewModel method to insert one single item
     * @param category object containing details of new Item to be inserted
     */
    public void insert(Category category) {
        repository.insert(category);
    }

    /**
     * ViewModel method to delete all Category records
     */
    public void deleteAllCategories() {
        repository.deleteAllCategories();
    }

    /**
     * ViewModel method to get all events
     * @return LiveData of type List<Event>
     */
    public LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }


    /**
     * ViewModel method to insert one single item
     * @param event object containing details of new Item to be inserted
     */
    public void insert(Event event) {
        repository.insert(event);
    }

    /**
     * ViewModel method to delete all Event records
     */
    public void deleteAllEvents() {
        repository.deleteAllEvents();
    }

    public void deleteEventById(String eventId) {
        Database.databaseWriteExecutor.execute(() -> repository.deleteEventById(eventId));
    }
}
