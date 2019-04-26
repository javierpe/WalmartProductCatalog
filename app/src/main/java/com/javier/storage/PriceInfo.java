package com.javier.storage;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PriceInfo extends RealmObject {
    private float originalPrice;
    private float specialPrice;
}
