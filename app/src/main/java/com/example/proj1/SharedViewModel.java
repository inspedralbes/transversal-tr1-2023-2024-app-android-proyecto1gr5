package com.example.proj1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Boolean> confirmationResult = new MutableLiveData<>();

    public void setConfirmationResult(boolean result) {
        confirmationResult.setValue(result);
    }

    public LiveData<Boolean> getConfirmationResult() {
        return confirmationResult;
    }
}


