package com.example.todolist1.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todolist1.ActionDatabase;
import com.example.todolist1.DAO.StatusDao;
import com.example.todolist1.Entities.Status;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatusRepository {

    private static StatusDao statusDao;
    private LiveData<List<Status>> allStatus;
    private LiveData<List<String>> allStatustitle;
    public StatusRepository(Application application){
        ActionDatabase database = ActionDatabase.getInstance(application);
        statusDao = database.statusDao();
        allStatus = statusDao.getAllStatus();
        allStatustitle = statusDao.getAllStatustitle();
    }

    public void inserts(Status status){

        new InsertAsyncTask(statusDao).execute(status);
    }

    public static Status getStatusById(int id) throws ExecutionException, InterruptedException {

        Status s = new GetAsyncTask(statusDao).execute(id).get();
        return s;
    }

    public void delete (Status status){

        new DeleteAsyncTask(statusDao).execute(status);
    }

    public LiveData<List<Status>> getAllStatus() {

        return allStatus;
    }

    public LiveData<List<String>> getAllStatustitle() {

        return allStatustitle;
    }

    private static class GetAsyncTask extends AsyncTask<Integer , Void , Status > {

        private StatusDao statusDao;
        Application application;
        ActionDatabase actionDatabase = ActionDatabase.getInstance(application);
        private GetAsyncTask (StatusDao statusDao){
            this.statusDao = statusDao;
        }

        @Override
        protected com.example.todolist1.Entities.Status doInBackground(Integer... integers) {
            statusDao = actionDatabase.statusDao();
            com.example.todolist1.Entities.Status status = statusDao.getStatusById(integers[0]);
            return status;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Status , Void , Void >{

        private StatusDao statusDao;
        private DeleteAsyncTask (StatusDao statusDao){
            this.statusDao = statusDao;
        }
        @Override
        protected Void doInBackground(com.example.todolist1.Entities.Status... statuses) {
            statusDao.delete(statuses[0]);
            return null;
        }
    }
    private static class InsertAsyncTask extends AsyncTask<Status , Void , Void >{

        private StatusDao statusDao;
        private InsertAsyncTask (StatusDao statusDao){
            this.statusDao = statusDao;
        }

        @Override
        protected Void doInBackground(com.example.todolist1.Entities.Status... statuses) {
            statusDao.inserts(statuses[0]);
            return null;
        }
    }
}
