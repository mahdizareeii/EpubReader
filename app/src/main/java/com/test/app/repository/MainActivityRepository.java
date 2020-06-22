package com.test.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.app.model.CustomSpinnerModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRepository {

    private MutableLiveData<List<CustomSpinnerModel>> listMutableLiveData = new MutableLiveData<>();

    public LiveData<List<CustomSpinnerModel>> getChaptersList(int size) {
        List<CustomSpinnerModel> chapters = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            chapters.add(new CustomSpinnerModel(i, "فصل " + i));
        }
        listMutableLiveData.setValue(chapters);
        return listMutableLiveData;
    }
}
