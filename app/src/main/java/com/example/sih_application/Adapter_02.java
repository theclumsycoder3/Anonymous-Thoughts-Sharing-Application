package com.example.sih_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_02 extends ArrayAdapter<String> {

    private ArrayList<String> arr;
    private ArrayList<String> arr1;

    public Adapter_02(@NonNull Context context, int resource, @NonNull ArrayList<String> arr, @NonNull ArrayList<String> arr1) {
        super(context, resource, arr);
        this.arr = arr;
        this.arr1 = arr1;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return arr.get(position);
    }

    @Nullable
    public String getItem1(int position) {
        return arr1.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_layout, parent, false);
        TextView t = convertView.findViewById(R.id.textView);
        TextView t1 = convertView.findViewById(R.id.userName);
        t1.setText("User: " + getItem1(position));
        t.setText(getItem(position));
        return convertView;
    }
}
