package com.example.javiersilva.tabssample;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by javiersilva on 4/1/17.
 */

public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_MESSAGE = "message";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static com.example.javiersilva.tabssample.PlaceholderFragment newInstance(@StringRes int message) {
        com.example.javiersilva.tabssample.PlaceholderFragment fragment = new com.example.javiersilva.tabssample.PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getString(getArguments().getInt(ARG_MESSAGE))));
        return rootView;
    }
}
