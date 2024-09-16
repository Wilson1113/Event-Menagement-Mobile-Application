package com.fit2081.assignment3.provider;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fit2081.assignment3.categories.Category;
import com.fit2081.assignment3.events.Event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Category.class, Event.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    // database name, this is important as data is contained inside a file named "card_database"
    public static final String DATABASE = "database_a3";

    // reference to DAO, here RoomDatabase parent class will implement this interface
    public abstract DAO _DAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Since this class is an absract class, to get the database reference we would need
     * to implement a way to get reference to the database.
     *
     * @param context Application of Activity Context
     * @return a reference to the database for read and write operation
     */
    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class, DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}