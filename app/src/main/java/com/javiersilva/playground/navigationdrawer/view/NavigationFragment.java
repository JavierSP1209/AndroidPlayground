package com.javiersilva.playground.navigationdrawer.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.javiersilva.playground.R;

/**
 * Created by javiersilva on 5/9/17.
 */
public class NavigationFragment extends Fragment {

    private NavigationListener navigationListener;
    private String message;

    public void setNavigationListener(NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        navigationListener.onToolbarLoaded(toolbar);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    public interface NavigationListener {
        void onToolbarLoaded(Toolbar toolbar);
    }
}
