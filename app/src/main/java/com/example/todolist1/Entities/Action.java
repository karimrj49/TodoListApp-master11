package com.example.todolist1.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.todolist1.Converter;

import java.util.Date;

@Entity(tableName = "action_table")
public class Action {

    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String title ;
    private String description ;
    private int idstatus ;
    private int idproject;
    private int idtag;
    @TypeConverters(Converter.class)
    private Date Tododate;


    public Date getTododate() {
        return Tododate;
    }

    public void setTododate(Date tododate) {
        Tododate = tododate;
    }

    @TypeConverters(Converter.class)
    private Date createdDate;


    @Ignore
    Status s;
    @Ignore
    Project p;
    @Ignore
    Tag t;
@Ignore
    public Action (String title , String description ,int idstatus,int idproject,int idtag){
        this.title = title;
        this.description = description;
        this.idstatus = idstatus;
        this.idproject = idproject;
        this.idtag = idtag;
    }
    public Action (String title , String description ,int idstatus,int idproject,int idtag,Date createdDate,Date Tododate){
        this.title = title;
        this.description = description;
        this.idstatus = idstatus;
        this.idproject = idproject;
        this.idtag = idtag;
        this.createdDate=createdDate;
        this.Tododate=Tododate;

    }

    @Ignore
    public Action() {
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }

    public int getIdproject() {
        return idproject;
    }

    public void setIdproject(int idproject) {
        this.idproject = idproject;
    }

    public int getIdtag() {
        return idtag;
    }

    public void setIdtag(int idtag) {
        this.idtag = idtag;
    }
}
