package com.example.todolist1.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todolist1.ActionDatabase;
import com.example.todolist1.DAO.TagDao;
import com.example.todolist1.Entities.Tag;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TagRepository {

    private static TagDao tagDao;
    private LiveData<List<Tag>> alltags;
    private LiveData<List<String>> alltagstitle;

    public TagRepository(Application application) {

        ActionDatabase database = ActionDatabase.getInstance(application);
        tagDao = database.tagDao();
        alltags = tagDao.getAllTag();
        alltagstitle = tagDao.getAllTagtitle();
    }

    public void insert(Tag tag){
        new InsertAsyncTask(tagDao).execute(tag);
    }

    public void delete(Tag tag){
        new DeleteAsyncTask(tagDao).execute(tag);
    }

    public Tag getTagById(int id) throws ExecutionException, InterruptedException {
        Tag tag = new GetAsyncTask(tagDao).execute(id).get();
        return tag;
    }

    public int getIdByTitle(String t) throws ExecutionException, InterruptedException {
        int id = new GetTAsyncTask(tagDao).execute(t).get();
        return id;
    }
    public LiveData<List<Tag>> getAllTags() {
        return alltags;
    }

    public LiveData<List<String>> getAllTagstitle(){
        return alltagstitle;
    }

    public class InsertAsyncTask extends AsyncTask <Tag,Void,Void>{

        private TagDao tagDao;
        public InsertAsyncTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.insert(tags[0]);
            return null;
        }
    }

    public class DeleteAsyncTask extends AsyncTask <Tag,Void,Void>{

        private TagDao tagDao;

        public DeleteAsyncTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.delete(tags[0]);
            return null;
        }
    }

    private static class GetAsyncTask extends AsyncTask<Integer , Void , Tag> {

        private TagDao tagDao;
        private GetAsyncTask (TagDao tagDao){
            this.tagDao = tagDao;
        }

        @Override
        protected Tag doInBackground(Integer... integers) {
            Tag tag = tagDao.getTagById(integers[0]);
            return tag;
        }
    }

    private static  class GetTAsyncTask extends AsyncTask<String,Void,Integer>{

        private TagDao tagDao;
        private GetTAsyncTask (TagDao tagDao){
            this.tagDao = tagDao;
        }
        @Override
        protected Integer doInBackground(String... strings) {
            int project = tagDao.getIdByTitle(strings[0]);
            return project;
        }
    }
}
