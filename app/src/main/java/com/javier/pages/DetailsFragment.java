package com.javier.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.javier.catalogoproductos.R;
import com.javier.storage.DynamicFacet;

import io.realm.RealmList;

public class DetailsFragment extends Fragment {

    private RecyclerView detailList;
    private RealmList<DynamicFacet> dynamicFacets;
    private DetailAdapter adapter;

    public RealmList<DynamicFacet> getDynamicFacets() {
        return dynamicFacets;
    }

    public void setDynamicFacets(RealmList<DynamicFacet> dynamicFacets) {
        this.dynamicFacets = dynamicFacets;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_details, container, false);

        detailList = rootView.findViewById(R.id.detailList);
        detailList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new DetailAdapter(getDynamicFacets());
        detailList.setAdapter(adapter);
        return rootView;
    }


    /**
     * Detail adapter
     */
    private class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailHolder>{

        private RealmList<DynamicFacet> facets;

        public DetailAdapter(RealmList<DynamicFacet> facets) {
            this.facets = facets;
        }

        @NonNull
        @Override
        public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.detail_item, parent, false);
            return new DetailHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailHolder holder, int position) {
            DynamicFacet facet = dynamicFacets.get(position);
            holder.lbDescription.setText(facet.getDescription());
            holder.lbValue.setText(facet.getValue());
        }

        @Override
        public int getItemCount() {
            return dynamicFacets.size();
        }

        public class DetailHolder extends RecyclerView.ViewHolder{

            private TextView lbDescription;
            private TextView lbValue;

            public DetailHolder(@NonNull View itemView) {
                super(itemView);
                lbDescription = itemView.findViewById(R.id.lbDescription);
                lbValue = itemView.findViewById(R.id.lbValue);
            }
        }
    }
}
