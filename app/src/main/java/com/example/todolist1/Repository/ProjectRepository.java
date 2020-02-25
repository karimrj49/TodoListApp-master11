package com.example.todolist1.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todolist1.ActionDatabase;
import com.example.todolist1.DAO.ProjectDao;
import com.example.todolist1.Entities.Project;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProjectRepository {

    private static ProjectDao projectDao;
    private LiveData<List<Project>> allproject;
    private LiveData<List<String>> allProjecttitle;
    public ProjectRepository (Application application){

        ActionDatabase database = ActionDatabase.getInstance(application);
        projectDao = database.projectDao();
        allproject = projectDao.getAllProject();
        allProjecttitle = projectDao.getAllProjecttitle();
    }
    public void update(Project projects){

        new ProjectRepository.UpdateAsyncTask(projectDao).execute(projects);
    }


    public void insert(Project projects){

        new ProjectRepository.InsertAsyncTask(projectDao).execute(projects);
    }

    public void delete (Project projects){

        new ProjectRepository.DeleteAsyncTask(projectDao).execute(projects);
    }

    public static Project getProjectById(int id) throws ExecutionException, InterruptedException {

        Project p = new GetAsyncTask(projectDao).execute(id).get();
        return p;
    }

    public static int getIdByTitle(String t) throws ExecutionException, InterruptedException {

        int p = new GetTAsyncTask(projectDao).execute(t).get();
        return p;
    }

    public LiveData<List<Project>> getAllproject() {

        return allproject;
    }

    public LiveData<List<String>> getAllProjecttitle() {

        return allProjecttitle;
    }

    private static class InsertAsyncTask extends AsyncTask<Project , Void , Void > {

        private ProjectDao ProjectDao;
        private InsertAsyncTask (ProjectDao projectDao){
            this.ProjectDao = projectDao;
        }
        @Override
        protected Void doInBackground(Project... project) {
            ProjectDao.insert(project[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Project , Void , Void > {

        private ProjectDao ProjectDao;
        private UpdateAsyncTask (ProjectDao projectDao){
            this.ProjectDao = projectDao;
        }
        @Override
        protected Void doInBackground(Project... project) {
            ProjectDao.update(project[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Project , Void , Void > {

        private ProjectDao ProjectDao;
        private DeleteAsyncTask (ProjectDao projectDao){
            this.ProjectDao = projectDao;
        }
        @Override
        protected Void doInBackground(Project... project) {
            ProjectDao.delete(project[0]);
            return null;
        }
    }

    private static class GetAsyncTask extends AsyncTask<Integer , Void , Project > {

        private ProjectDao projectDao;
        private GetAsyncTask (ProjectDao projectDao){
            this.projectDao = projectDao;
        }

        @Override
        protected Project doInBackground(Integer... integers) {
            Project project = projectDao.getProjectById(integers[0]);
            return project;
        }
    }

    private static  class GetTAsyncTask extends AsyncTask<String,Void,Integer>{

        private ProjectDao projectDao;
        private GetTAsyncTask (ProjectDao projectDao){
            this.projectDao = projectDao;
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int project = projectDao.getIdByTitle(strings[0]);
            return project;
        }
    }
}
