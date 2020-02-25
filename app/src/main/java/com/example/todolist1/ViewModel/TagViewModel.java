package com.example.todolist1.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist1.Entities.Tag;
import com.example.todolist1.Repository.TagRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TagViewModel extends AndroidViewModel {
    private static TagRepository tagRepository;
    private LiveData<List<Tag>> allTag;
    private LiveData<List<String>> allTagTitle;
    public TagViewModel(@NonNull Application application) {
        super(application);
        tagRepository = new TagRepository(application);
        allTag = tagRepository.getAllTags();
        allTagTitle = tagRepository.getAllTagstitle();
    }

    public void insert(Tag tag){ tagRepository.insert(tag); }

    public void delete(Tag tag){ tagRepository.delete(tag); }

    public LiveData<List<Tag>> getAllTag() {
        return allTag;
    }

    public LiveData<List<String>> getAllTagtitle (){ return allTagTitle ;}

    public static synchronized Tag getTagById(int id) throws ExecutionException, InterruptedException {
        Tag tag = tagRepository.getTagById(id);
        return tag;
    }

    public static synchronized int getIdByTitle(String t) throws ExecutionException, InterruptedException {
        int tag = tagRepository.getIdByTitle(t);
        return tag;
    }
}
