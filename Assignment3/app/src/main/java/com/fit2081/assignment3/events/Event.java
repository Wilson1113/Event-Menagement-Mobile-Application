package com.fit2081.assignment3.events;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Event.TABLE_NAME)
public class Event {
    public static final String TABLE_NAME = "events";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "eventId")
    private String eventId;

    @ColumnInfo(name = "categoryId")
    private String categoryId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "ticketsAvailable")
    private int ticketsAvailable;

    @ColumnInfo(name = "isActive?")
    private boolean isActive;

    public Event(String eventId, String categoryId, String name, int ticketsAvailable, boolean isActive) {
        this.eventId = eventId;
        this.categoryId = categoryId;
        this.name = name;
        this.ticketsAvailable = ticketsAvailable;
        this.isActive = isActive;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
