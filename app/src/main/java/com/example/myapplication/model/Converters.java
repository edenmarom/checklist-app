package com.example.myapplication.model;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromArray(List<String> strings) {
        String string = "";

        if (strings != null) {
            for(String s : strings) string += (s + ",");
        }

        return string;
    }

    @TypeConverter
    public List<String> toArray(String concatenatedStrings) {
        List<String> myStrings = new ArrayList<>();

        for(String s : concatenatedStrings.split(",")) {
            myStrings.add(s);
        };

        return myStrings;
    }
}