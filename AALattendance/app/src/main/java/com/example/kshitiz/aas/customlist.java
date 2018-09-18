package com.example.kshitiz.aas;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class customlist extends ArrayAdapter<attitem> {
    public customlist(@NonNull Context context, int resource, List<attitem> la) {
        super(context, resource,la);
    }
    @Override
    public View getView(int position, View v, ViewGroup parent) {


        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.child, null);
        }

        attitem p = getItem(position);

        if (p != null) {
            TextView tt1 = v.findViewById(R.id.listItem);

            if (tt1 != null) {
                tt1.setText(p.getName());
                if(p.isStatus())
                    tt1.setTextColor(Color.GREEN);
                else
                    tt1.setTextColor(Color.RED);
            }
        }

        return v;
    }
}
