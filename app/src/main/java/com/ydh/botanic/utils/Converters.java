package com.ydh.botanic.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ydh.botanic.models.Category;
import com.ydh.botanic.models.Garden;
import com.ydh.botanic.models.Image;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Garden> stringToGardentList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Garden>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String gardenListToString(List<Garden> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<Image> stringToImageList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Image>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String imageListToString(List<Image> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<Category> stringToCategoryList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Category>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String categoryListToString(List<Category> someObjects) {
        return gson.toJson(someObjects);
    }
}
