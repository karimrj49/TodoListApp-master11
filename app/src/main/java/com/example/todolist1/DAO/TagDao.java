package com.example.todolist1.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todolist1.Entities.Tag;

import java.util.List;

@Dao
public interface TagDao {

  @Insert
  void insert(Tag tag);

  @Delete
  void delete(Tag tag);

    @Query("SELECT * FROM tag_table")
    LiveData<List<Tag>> getAllTag();

    @Query("SELECT title FROM tag_table")
    LiveData<List<String>> getAllTagtitle();

    @Query("Select * from tag_table WHERE idtag = :id")
    Tag getTagById(int id);

    @Query("SELECT idtag FROM tag_table WHERE title=:title")
    int getIdByTitle(String title);
}
