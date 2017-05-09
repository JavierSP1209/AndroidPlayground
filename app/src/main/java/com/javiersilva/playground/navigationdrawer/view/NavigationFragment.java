package com.javiersilva.playground.navigationdrawer.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javiersilva.playground.R;

/**
 * Created by javiersilva on 5/9/17.
 */
public class NavigationFragment extends Fragment {

    private NavigationListener navigationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        setUpToolbar(toolbar);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NavigationListener){
            navigationListener = (NavigationListener) context;
        }
    }

    protected void setUpToolbar(Toolbar toolbar) {
        navigationListener.onToolbarLoaded(toolbar);
    }

    public interface NavigationListener {
        void onToolbarLoaded(Toolbar toolbar);
    }
}
