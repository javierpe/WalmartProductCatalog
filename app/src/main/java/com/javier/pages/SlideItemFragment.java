package com.javier.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import com.javier.catalogoproductos.R;
import com.squareup.picasso.Picasso;

public class SlideItemFragment extends Fragment {

    private ImageView img;

    private String pictureUrl;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_slide_item, container, false);

        img = rootView.findViewById(R.id.img);
        if(getPictureUrl() != null){
            Picasso.get().load("https://www.walmart.com.mx" + getPictureUrl()).into(img);
        }
        return rootView;
    }

}
