package com.example.sih_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Adapter_01 extends ArrayAdapter<String> {

    private ArrayList<String> arr;

    public Adapter_01(@NonNull Context context, int resource, @NonNull ArrayList<String> arr) {
        super(context, resource, arr);
        this.arr = arr;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return arr.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        TextView t = convertView.findViewById(R.id.textView);
        t.setText(getItem(position));
        return convertView;
    }
}
