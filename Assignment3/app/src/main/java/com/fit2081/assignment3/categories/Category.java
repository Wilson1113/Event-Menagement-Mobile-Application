package com.fit2081.assignment3.categories;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Category.TABLE_NAME)
public class Category {
    public static final String TABLE_NAME = "categories";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "categoryId")
    private String categoryId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "count")
    private int count;

    @ColumnInfo(name = "isActive?")
    private boolean isActive;

    @ColumnInfo(name = "eventLocation")
    private String eventLocation;

    public Category(String categoryId, String name, int count, boolean isActive, String eventLocation) {
        this.categoryId = categoryId;
        this.name = name;
        this.count = count;
        this.isActive = isActive;
        this.eventLocation = eventLocation;
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

    public int getCount() {
        return count;
    }

    public void addCount() {
        this.count += 1;
    }

    public void minusCount() {
        this.count -= 1;
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

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
