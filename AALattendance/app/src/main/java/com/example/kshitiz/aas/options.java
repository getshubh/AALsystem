package com.example.kshitiz.aas;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class options extends Fragment {
View v;

    public options() {
        // Required empty public constructor
    }
Button b;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=(View)inflater.inflate(R.layout.fragment_options, container, false);

        return  v;
    }

}
