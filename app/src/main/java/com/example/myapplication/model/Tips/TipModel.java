package com.example.myapplication.model.Tips;


import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TipModel {
    final public static TipModel instance = new TipModel();

    final String BASE_URL = "https://api.adviceslip.com/";
    Retrofit retrofit;
    TipApi tipApi;

    private TipModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        tipApi = retrofit.create(TipApi.class);
    }

    public LiveData<List<Tip>> getTip(){
        System.out.println("Adddiiiiiiiiiiiiiii 1");
        MutableLiveData<List<Tip>> data = new MutableLiveData<>();

        Call<TipSearchResult> call = tipApi.getTip();
        call.enqueue(new Callback<TipSearchResult>() {
            @Override
            public void onResponse(Call<TipSearchResult> call, Response<TipSearchResult> response) {
                if(response.isSuccessful()){
                    TipSearchResult res = response.body();
                    //todo, check if problem
                    data.setValue(res.getList());
                    System.out.println("Return to the kitchen ");
                    System.out.println(res.getList().get(0).toString());

                } else {
                    System.out.println("Adddiiiiiiiiiiiiiii 3");
                    Log.d("TAG","---------- getTipById response error");

                }
            }

            @Override
            public void onFailure(Call<TipSearchResult> call, Throwable t) {
                Log.d("TAG","---------- getTipById failed");
            }
        });

        return data;
    }

}