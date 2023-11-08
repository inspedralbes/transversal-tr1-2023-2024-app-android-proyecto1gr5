package com.example.proj1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> selectedProductQuantity = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedProductPosition = new MutableLiveData<>();

    public void selectProduct(int position, int quantity) {
        selectedProductPosition.setValue(position);
        selectedProductQuantity.setValue(quantity);
    }

    public LiveData<Integer> getSelectedProductQuantity() {
        return selectedProductQuantity;
    }

    public LiveData<Integer> getSelectedProductPosition() {
        return selectedProductPosition;
    }
}

