package com.javier.catalogoproductos;

import com.javier.api.ProductCalls;
import com.javier.mvp.main.MainLayer;
import com.javier.storage.Product;

import javax.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainLayer.Presenter {

    @Nullable
    private MainLayer.View mainView;
    private MainLayer.Model mainModel;

    public MainPresenter(MainLayer.Model model){
        this.mainModel = model;
    }

    @Override
    public void setView(MainLayer.View view) {
        this.mainView = view;
    }

    @Override
    public Disposable requestProduct(String productId) {
        return ProductCalls.getProduct(productId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(product -> {
                    if(product != null){
                        if(mainModel.createProduct(product)){
                            if(mainView != null) {
                                mainView.onRequestProductSuccess(product);
                            }
                        }
                    }else {
                        if(mainView != null){
                            mainView.requestProductError("Error al leer la información del producto");
                        }
                    }
                }, throwable -> {
                    if(mainView != null){
                        mainView.requestProductError(throwable.getMessage());
                    }
                    throwable.printStackTrace();
                });
    }

    @Override
    public void loadProduct() {
        Product product = mainModel.getProduct();
        if(product != null){
            if(mainView != null) {
                mainView.onRequestProductSuccess(product);
            }
        }
    }
}
