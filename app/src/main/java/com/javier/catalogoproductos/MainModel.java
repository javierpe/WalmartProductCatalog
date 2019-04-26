package com.javier.catalogoproductos;

import com.javier.mvp.main.MainLayer;
import com.javier.mvp.main.MainMemory;
import com.javier.storage.Product;


public class MainModel implements MainLayer.Model {
    private MainMemory mainMemory;

    public MainModel(MainMemory mainMemory){
        this.mainMemory = mainMemory;
    }

    @Override
    public boolean createProduct(Product product) {
        if(product != null) {
            return mainMemory.saveProduct(product);
        }

        return false;
    }

    @Override
    public Product getProduct() {
        return mainMemory.getProduct();
    }
}
