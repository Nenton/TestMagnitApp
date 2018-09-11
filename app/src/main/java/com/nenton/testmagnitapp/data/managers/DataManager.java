package com.nenton.testmagnitapp.data.managers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.nenton.testmagnitapp.data.json.ResultParse;
import com.nenton.testmagnitapp.ui.fragments.TimeTableFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataManager {
    private static DataManager INSTANCE;
    private DataManager(){

    }

    public static DataManager getInstance() {
        if (INSTANCE == null){
            synchronized (DataManager.class){
                INSTANCE = new DataManager();
            }
        }
        return INSTANCE;
    }

    public ResultParse readFile(Context context) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("allStations.json")))) {
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
        } catch (IOException e) {
            Log.e(TimeTableFragment.class.getName(), e.getMessage(), e);
        }
        Gson gson = new Gson();
        return gson.fromJson(builder.toString(), ResultParse.class);
    }
}
