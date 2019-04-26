package com.javier.mvp.main;

import com.javier.storage.Product;

import io.reactivex.disposables.Disposable;

public interface MainLayer {
    interface Model{
        boolean createProduct(Product product);
        Product getProduct();
    }

    interface View{
        void requestProductError(String message);
        void onRequestProductSuccess(Product product);
    }

    interface Presenter{
        void setView(MainLayer.View view);
        Disposable requestProduct(String productId);
        void loadProduct();
    }
}
