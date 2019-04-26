package com.javier.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.javier.catalogoproductos.R;

public class DescriptionFragment extends Fragment {

    private TextView lbDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_description, container, false);

        lbDescription = rootView.findViewById(R.id.lbDescription);
        Bundle b = getArguments();
        if(b != null && b.containsKey("DESCRIPTION")){
            lbDescription.setText(b.getString("DESCRIPTION"));
        }
        return rootView;
    }
}
