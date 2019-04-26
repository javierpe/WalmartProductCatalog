package com.javier.api;

import com.javier.storage.Product;

import io.reactivex.Observable;

public class ProductCalls {
    public static Observable<Product> getProduct(String productId){
        return CallProvider.call().getProduct(productId);
    }
}
