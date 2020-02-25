package com.example.todolist1.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist1.Entities.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Delete
    void delete(Project project);

    @Update
    void update(Project project);

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAllProject();

    @Query("SELECT Titlet FROM project_table")
    LiveData<List<String>> getAllProjecttitle();

    @Query("Select * from project_table WHERE projectid = :id")
    Project getProjectById(int id);

    @Query("SELECT projectid FROM project_table WHERE Titlet=:title")
    int getIdByTitle(String title);
}
