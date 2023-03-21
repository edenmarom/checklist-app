package com.example.myapplication.ui.Tips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Tips.Tip;
import com.example.myapplication.model.Tips.TipModel;

import java.util.List;

public class TipsFragmentViewModel extends ViewModel {

    private LiveData<List<Tip>> data = TipModel.instance.getTip();

    public LiveData<List<Tip>> getData(){
        return data;
    }

}
