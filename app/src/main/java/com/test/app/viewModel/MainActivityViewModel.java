package com.test.app.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.test.app.model.CustomSpinnerModel;
import com.test.app.repository.MainActivityRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    public LiveData<List<CustomSpinnerModel>> getChaptersList(int size) {
        return new MainActivityRepository().getChaptersList(size);
    }

}
