package com.example.mentalitree.ui.motivation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MotivationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MotivationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is motivation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}