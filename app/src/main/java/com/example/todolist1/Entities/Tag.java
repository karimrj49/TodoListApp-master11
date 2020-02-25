package com.example.todolist1.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag_table")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    private int idtag;
    private String title;

    @Ignore
    public Tag() {
    }

    public Tag(String title) {
        this.title = title;
    }

    public int getIdtag() {
        return idtag;
    }

    public String getTitle() {
        return title;
    }

    public void setIdtag(int idtag) {
        this.idtag = idtag;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
