package com.example.todolist1.Repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.todolist1.ActionDatabase;
import com.example.todolist1.DAO.ActionDao;
import com.example.todolist1.Entities.Action;

import java.util.List;

public class ActionRepository {

    private ActionDao actionDao;
    private LiveData<List<Action>>allAction;
    private LiveData<List<Action>>allActionS;
    //int p;
    public ActionRepository(Application application){

        ActionDatabase database = ActionDatabase.getInstance(application);
        actionDao = database.actionDao();
        allAction = actionDao.getAllAction();
        //allActionS = actionDao.getActionsS(p);
    }

    public void insert(Action action){

        new InsertAsyncTask(actionDao).execute(action);
    }

    public void delete (Action action){

        new DeleteAsyncTask(actionDao).execute(action);
    }

    public void update(Action action){

        new UpdateAsyncTask(actionDao).execute(action);
    }

    public LiveData<List<Action>> getAllAction() {

        return allAction;
    }

    public LiveData<List<Action>>getAllActionsS(int p){
        return actionDao.getActionsS(p);
    }

    public LiveData<List<Action>>getAllActionsP(int p){
        return actionDao.getActionP(p);
    }

    public LiveData<List<Action>>getAllActionsT(int p){
        return actionDao.getActionsT(p);
    }

    private static class InsertAsyncTask extends AsyncTask<Action , Void , Void >{

        private ActionDao actionDao;
        private InsertAsyncTask (ActionDao actionDao){
            this.actionDao = actionDao;
        }
        @Override
        protected Void doInBackground(Action... actions) {
            actionDao.insert(actions[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Action , Void , Void >{

        private ActionDao actionDao;
        private UpdateAsyncTask (ActionDao actionDao){
            this.actionDao = actionDao;
        }
        @Override
        protected Void doInBackground(Action... actions) {
            actionDao.update(actions[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Action , Void , Void >{

        private ActionDao actionDao;
        private DeleteAsyncTask (ActionDao actionDao){
            this.actionDao = actionDao;
        }
        @Override
        protected Void doInBackground(Action... actions) {
            actionDao.delete(actions[0]);
            return null;
        }
    }
}
