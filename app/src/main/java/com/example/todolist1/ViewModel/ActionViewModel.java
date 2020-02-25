package com.example.todolist1.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist1.Entities.Action;
import com.example.todolist1.Repository.ActionRepository;

import java.util.List;

public class ActionViewModel extends AndroidViewModel {

    private ActionRepository actionRepository;
    private LiveData<List<Action>>allAction;
    //private LiveData<List<Action>>allActionS;

    public ActionViewModel(@NonNull Application application) {
        super(application);
        actionRepository = new ActionRepository(application);
        allAction = actionRepository.getAllAction();
        //allActionS = actionRepository.getAllActionS(p);
    }

    public void update(Action action){ actionRepository.update(action);}

    public void insert(Action action){
        actionRepository.insert(action);
    }

    public void delete(Action action){
        actionRepository.delete(action);
    }

    public LiveData<List<Action>> getAllAction() {
        return allAction;
    }

    public LiveData<List<Action>> getAllActionsS(int p){return  actionRepository.getAllActionsS(p);}

    public LiveData<List<Action>> getAllActionsP(int p){
        return actionRepository.getAllActionsP(p);
    }

    public LiveData<List<Action>> getAllActionsT(int p){
        return actionRepository.getAllActionsT(p);
    }
}
