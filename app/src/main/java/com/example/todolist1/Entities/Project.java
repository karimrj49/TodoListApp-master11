package com.example.todolist1.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "project_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int projectid;
    private String Titlet;

    public Project() {

    }

    public Project(String title) {
        this.Titlet = title;
    }

    public String getTitlet() {
        return Titlet;
    }

    public void setTitlet(String titlet) {
        this.Titlet = titlet;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int id) {
        this.projectid = id;
    }

}
