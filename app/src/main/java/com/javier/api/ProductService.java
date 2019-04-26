package com.javier.api;

import com.javier.storage.Product;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ProductService {
    @Headers("{accept: application/json, connection: keep-alive}")
    @GET("rest/model/atg/commerce/catalog/ProductCatalogActor/getProduct")
    Observable<Product> getProduct(@Query("id") String productId);
}
