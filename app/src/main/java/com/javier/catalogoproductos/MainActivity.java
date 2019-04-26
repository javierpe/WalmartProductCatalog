package com.javier.catalogoproductos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.javier.mvp.App;
import com.javier.mvp.main.MainLayer;
import com.javier.pages.DescriptionFragment;
import com.javier.pages.DetailsFragment;
import com.javier.pages.InfoFragment;
import com.javier.pages.SlideItemFragment;
import com.javier.storage.OfferItem;
import com.javier.storage.Product;
import com.rd.PageIndicatorView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity implements MainLayer.View{

    @Inject
    MainLayer.Presenter presenter;

    private ViewPager viewPager;
    private ViewPager slide;
    private RecyclerView offerList;
    private OfferAdapter offerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private PageIndicatorView pageIndicatorView;
    private DescriptionFragment descriptionFragment;
    private TabLayout tabLayout;

    private ScreenSlidePagerAdapter slideImagesAdapter;
    private ScreenSlidePagerAdapter slideInfoAdapter;

    private TextView lbProductTitle;
    private TextView lbProductBrand;
    private TextView lbProductStatus;
    private TextView lbProductPrice;
    private TextView lbMaxMSI;
    private TextView lbBefore;
    private TextView lbSaveAmount;

    private FrameLayout btExit;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ((App) getApplication()).getApplicationComponent().inject(this);

        // Start Realm
        startRealm();

        // Get views
        setup();

        swipeRefreshLayout.setOnRefreshListener(() -> requestProduct());
        requestProduct();
    }

    private void requestProduct(){
        presenter.requestProduct("00750940180662");
    }

    /**
     * Views
     */
    private void setup(){
        viewPager = findViewById(R.id.viewPager);
        slide = findViewById(R.id.slide);
        lbProductTitle = findViewById(R.id.lbProductTitle);
        lbProductBrand = findViewById(R.id.lbProductBrand);
        lbProductStatus = findViewById(R.id.lbProductStatus);
        lbProductPrice = findViewById(R.id.lbProductPrice);
        lbBefore = findViewById(R.id.lbBefore);
        lbMaxMSI = findViewById(R.id.lbMaxMSI);
        btExit = findViewById(R.id.btExit);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        offerList = findViewById(R.id.offerList);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        offerList.setLayoutManager(linearLayoutManager);
        findViewById(R.id.btExit).setOnClickListener(v -> finish());
        tabLayout = findViewById(R.id.tabLayout);
        lbSaveAmount = findViewById(R.id.lbSaveAmount);
        viewPager.setOnTouchListener((v, event) -> true);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    viewPager.setCurrentItem(0);
                }else if(tab.getPosition() == 1){
                    viewPager.setCurrentItem(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Start Realm
     */
    private void startRealm(){
        // Start Realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
    }

    @Override
    public void requestProductError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestProductSuccess(Product product) {


        lbMaxMSI.setText(Html.fromHtml(String.format("Hasta <b>%s</b> meses sin intereses", product.getMaxMSI())));

        // Set slide
        ArrayList<Fragment> images = new ArrayList<>();

        SlideItemFragment left = new SlideItemFragment();
        left.setPictureUrl(product.getLeftLargeImageUrl());

        SlideItemFragment right = new SlideItemFragment();
        right.setPictureUrl(product.getRightLargeImageUrl());

        SlideItemFragment large = new SlideItemFragment();
        large.setPictureUrl(product.getLargeImageUrl());

        SlideItemFragment top = new SlideItemFragment();
        top.setPictureUrl(product.getSuperiorLargeImageUrl());

        images.add(large);
        images.add(left);
        images.add(right);
        images.add(top);

        slideImagesAdapter = new ScreenSlidePagerAdapter(images, getSupportFragmentManager());
        slide.setAdapter(slideImagesAdapter);

        slide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // Set Offers
        // Select first
        product.getOfferItems().get(0).setSelected(true);
        offerAdapter = new OfferAdapter(product.getOfferItems());
        offerList.setAdapter(offerAdapter);
        findViewById(R.id.btRight).setOnClickListener(v -> offerList.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1));
        findViewById(R.id.btLeft).setOnClickListener(v -> {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 0) {
                offerList.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
            } else {
                offerList.smoothScrollToPosition(0);
            }
        });

        // Set info
        ArrayList<Fragment> infoPages = new ArrayList<>();
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setDescription(Html.fromHtml(product.getSeoDescription()) + "\n" + product.getLongDescription());
        infoPages.add(infoFragment);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setDynamicFacets(product.getDynamicFacets());
        infoPages.add(detailsFragment);

        slideInfoAdapter = new ScreenSlidePagerAdapter(infoPages, getSupportFragmentManager());
        viewPager.setAdapter(slideInfoAdapter);

        lbProductTitle.setText(product.getDisplayName());
        lbProductBrand.setText(product.getBrand());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadProduct();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> pages;

        public ScreenSlidePagerAdapter(ArrayList<Fragment> pages, FragmentManager fm) {
            super(fm);
            this.pages = pages;
        }

        @Override
        public Fragment getItem(int position) {
            return pages.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }

    private class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferHolder>{

        private RealmList<OfferItem> offerItems;

        public OfferAdapter(RealmList<OfferItem> offerItems) {
            this.offerItems = offerItems;
        }

        @NonNull
        @Override
        public OfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.offer_item, parent, false);
            return new OfferHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull OfferHolder holder, int position) {
            OfferItem offerItem = offerItems.get(position);
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());

            holder.lbProductStatus.setText(offerItem.isRefurbished() ? "Reacondicionado" : "Nuevo");
            if(offerItem.getPriceInfo().getSpecialPrice() < offerItem.getPriceInfo().getOriginalPrice()){
                holder.lbBefore.setVisibility(View.VISIBLE);
                holder.lbBefore.setText(format.format(offerItem.getPriceInfo().getOriginalPrice()));
            }else {
                holder.lbBefore.setVisibility(View.GONE);
            }

            holder.lbProductPrice.setText(format.format(offerItem.getPriceInfo().getSpecialPrice()));
            holder.lbSeller.setText(Html.fromHtml(String.format("Vendido por <font color=\"#027ADB\">%s</font>", offerItem.getSellerName())));
            holder.offerItemView.setOnClickListener(v -> {
                for(OfferItem item : offerItems){
                    item.setSelected(false);
                }

                offerItem.setSelected(true);
                notifyDataSetChanged();

                // Change main info in view
                configureOfferInView(offerItem, format);
            });

            if(!offerItem.isSelected()){
                GradientDrawable drawable = (GradientDrawable)holder.selectorView.getBackground();
                drawable.setStroke(3, Color.TRANSPARENT);
                configureOfferInView(offerItem, format);
            }else {
                GradientDrawable drawable = (GradientDrawable)holder.selectorView.getBackground();
                drawable.setStroke(3, getColor(R.color.colorAccent));
            }
        }

        /**
         * Show info in main view.
         *
         * @param offerItem
         * @param format
         */
        private void configureOfferInView(OfferItem offerItem, NumberFormat format){
            lbProductStatus.setText(offerItem.isRefurbished() ? "Reacondicionado" : "Nuevo");
            if(offerItem.getPriceInfo().getSpecialPrice() < offerItem.getPriceInfo().getOriginalPrice()){
                lbBefore.setVisibility(View.VISIBLE);
                lbBefore.setText(format.format(offerItem.getPriceInfo().getOriginalPrice()));
                float saveAmount = offerItem.getPriceInfo().getOriginalPrice() - offerItem.getPriceInfo().getSpecialPrice();
                lbSaveAmount.setText(String.format("ahorras %s", format.format(saveAmount)));
                lbSaveAmount.setVisibility(View.VISIBLE);
            }else {
                lbBefore.setVisibility(View.GONE);
                lbSaveAmount.setVisibility(View.GONE);
            }

            lbProductPrice.setText(format.format(offerItem.getPriceInfo().getSpecialPrice()));
        }

        @Override
        public int getItemCount() {
            return offerItems.size();
        }

        private class OfferHolder extends RecyclerView.ViewHolder{

            private TextView lbProductStatus;
            private TextView lbBefore;
            private TextView lbProductPrice;
            private TextView lbSeller;
            private LinearLayout offerItemView;
            private FrameLayout selectorView;

            public OfferHolder(@NonNull View itemView) {
                super(itemView);
                lbProductStatus = itemView.findViewById(R.id.lbProductStatus);
                selectorView = itemView.findViewById(R.id.selectorView);
                lbBefore = itemView.findViewById(R.id.lbBefore);
                lbProductPrice = itemView.findViewById(R.id.lbProductPrice);
                offerItemView = itemView.findViewById(R.id.offerItemView);
                lbSeller = itemView.findViewById(R.id.lbSeller);
            }
        }
    }
}
