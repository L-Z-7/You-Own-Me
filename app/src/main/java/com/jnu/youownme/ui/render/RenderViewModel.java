package com.jnu.youownme.ui.render;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RenderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RenderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is render fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}