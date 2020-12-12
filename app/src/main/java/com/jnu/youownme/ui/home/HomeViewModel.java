package com.jnu.youownme.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jnu.youownme.dataprocessor.DataBank;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String mStr;

    public void A(String s){
        mStr = s;
    }

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        mText.setValue(mStr);

        return mText;
    }
}