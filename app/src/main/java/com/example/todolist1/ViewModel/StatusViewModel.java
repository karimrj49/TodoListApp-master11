package com.example.todolist1.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist1.Entities.Status;
import com.example.todolist1.Repository.StatusRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatusViewModel extends AndroidViewModel {

    private static StatusRepository statusRepository;
    private LiveData<List<Status>> allStatus;
    private LiveData<List<String>> allStatusTitle;
    public StatusViewModel(@NonNull Application application) {
        super(application);
        statusRepository = new StatusRepository(application);
        allStatus = statusRepository.getAllStatus();
        allStatusTitle = statusRepository.getAllStatustitle();
    }

    public void inserts(Status status){
        statusRepository.inserts(status);
    }

    public void delete(Status status){
        statusRepository.delete(status);
    }

    public static synchronized Status getStatusById(int id) throws ExecutionException, InterruptedException {
        Status status = statusRepository.getStatusById(id);
        return status;
    }

    public LiveData<List<Status>> getAllStatus (){ return allStatus ;}

    public LiveData<List<String>> getAllStatustitle (){ return allStatusTitle ;}
}
