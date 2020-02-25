package com.example.todolist1.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist1.Entities.Action;
import com.example.todolist1.Entities.Status;

import java.util.List;

@Dao
public interface ActionDao {

    @Insert
    void insert(Action action);

    @Delete
    void delete(Action action);

    @Query("SELECT * FROM action_table WHERE idstatus = 0 AND idproject = 0 AND idtag = 0")
    LiveData<List<Action>> getAllAction();

    @Query("SELECT * FROM action_table WHERE idstatus = :id")
    LiveData<List<Action>>getActionsS(int id);

    @Query("SELECT * FROM action_table WHERE idproject = :id")
    LiveData<List<Action>>getActionP(int id);

    @Query("SELECT * FROM action_table WHERE idtag = :id")
    LiveData<List<Action>>getActionsT(int id);
    @Update
    void update(Action action);
}
