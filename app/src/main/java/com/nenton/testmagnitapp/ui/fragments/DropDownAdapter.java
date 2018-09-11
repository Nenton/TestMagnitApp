package com.nenton.testmagnitapp.ui.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.nenton.testmagnitapp.data.json.Cities;
import com.nenton.testmagnitapp.data.json.Station;

import java.util.List;

public class DropDownAdapter extends ArrayAdapter {

    public DropDownAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    public static DropDownAdapter initAdapter(@NonNull Context context, int resource, List<Station> strings){
        DropDownAdapter dropDownAdapter = new DropDownAdapter(context, resource, strings);
        dropDownAdapter.notifyDataSetChanged();
        return dropDownAdapter;
    }
}
