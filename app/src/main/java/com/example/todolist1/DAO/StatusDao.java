package com.example.todolist1.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todolist1.Entities.Status;

import java.util.List;

@Dao
public interface StatusDao  {

    @Query("SELECT * FROM status_table")
    LiveData<List<Status>> getAllStatus();

    @Query("SELECT title FROM status_table")
    LiveData<List<String>> getAllStatustitle();

    @Insert
    void inserts(Status status);

    @Query("Select * from status_table WHERE statusid = :id")
    Status getStatusById(int id);

    @Delete
    void delete(Status status);


}
