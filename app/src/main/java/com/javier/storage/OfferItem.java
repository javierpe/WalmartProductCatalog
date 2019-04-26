package com.javier.storage;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OfferItem extends RealmObject {
    @SerializedName("sellerId")
    private int id;
    private String sellerName;
    private boolean active;
    private boolean invAvailable;
    private int offerRank;
    private int warrantyInMonths;
    private String offerType;
    private boolean refurbished;
    private String warranty;
    private String warrantyCondition;
    private PriceInfo priceInfo;

    @Ignore
    private boolean selected;
}
