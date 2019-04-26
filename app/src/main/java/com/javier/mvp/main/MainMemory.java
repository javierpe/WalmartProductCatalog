package com.javier.mvp.main;

import com.javier.storage.Product;
import javax.inject.Inject;

import io.realm.Realm;

public class MainMemory implements MainRepo{

    @Inject
    public MainMemory(){ }

    @Override
    public boolean saveProduct(Product product) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(product);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            realm.close();
        }
        return true;
    }

    @Override
    public Product getProduct() {
        Realm realm = Realm.getDefaultInstance();
        Product mProduct = Product.getProduct(realm);
        if(mProduct != null) {
            mProduct = realm.copyFromRealm(mProduct);
        }
        realm.close();
        return mProduct;
    }
}
