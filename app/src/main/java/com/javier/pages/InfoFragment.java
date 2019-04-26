package com.javier.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.javier.catalogoproductos.R;

public class InfoFragment extends Fragment {

    private TextView lbDescription;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_info, container, false);

        lbDescription = rootView.findViewById(R.id.lbDescription);
        lbDescription.setText(getDescription().isEmpty() ? "Sin descripción" : getDescription());
        return rootView;
    }
}
