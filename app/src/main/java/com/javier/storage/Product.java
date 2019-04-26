package com.javier.storage;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.javier.storage.deserializers.ProductDeserializer;

import javax.annotation.Nonnull;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonAdapter(ProductDeserializer.class)
public class Product extends RealmObject {
    @PrimaryKey
    private String id;
    @SerializedName("metaTitle")
    private String title;
    private String metaDescription;
    private String description;
    private String displayName;
    private String longDescription;
    private String brand;
    private String thumbnailImageUrl;
    private int maxMSI;
    private String rightLargeImageUrl;
    private String leftLargeImageUrl;
    private String superiorLargeImageUrl;
    private String largeImageUrl;
    private String seoDescription;
    private RealmList<DynamicFacet> dynamicFacets;
    private RealmList<OfferItem> offerItems;

    public static Product getProduct(@Nonnull Realm realm){
        return realm.where(Product.class).findFirst();
    }

}
