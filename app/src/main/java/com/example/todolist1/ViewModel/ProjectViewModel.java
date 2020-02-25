package com.example.todolist1.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist1.Entities.Project;
import com.example.todolist1.Repository.ProjectRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProjectViewModel extends AndroidViewModel {

    private static ProjectRepository projectRepository;
    private LiveData<List<Project>> allProject;
    private LiveData<List<String>> allProjectTitle;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
        allProject = projectRepository.getAllproject();
        allProjectTitle = projectRepository.getAllProjecttitle();
        //allActionS = actionRepository.getAllActionS(p);
    }

    public void update(Project projects){ projectRepository.update(projects);}

    public void insert(Project projects){ projectRepository.insert(projects); }

    public void delete(Project projects){ projectRepository.delete(projects); }

    public LiveData<List<Project>> getAllProject() {
        return allProject;
    }

    public LiveData<List<String>> getAllProjecttitle (){ return allProjectTitle ;}

    public static synchronized Project getProjectById(int id) throws ExecutionException, InterruptedException {
        Project project = projectRepository.getProjectById(id);
        return project;
    }

    public static synchronized int getIdByTitle(String t) throws ExecutionException, InterruptedException {
        int project = projectRepository.getIdByTitle(t);
        return project;
    }
}
