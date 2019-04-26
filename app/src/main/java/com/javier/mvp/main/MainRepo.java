package com.javier.mvp.main;

import com.javier.storage.Product;

public interface MainRepo {
    boolean saveProduct(Product product);
    Product getProduct();
}
