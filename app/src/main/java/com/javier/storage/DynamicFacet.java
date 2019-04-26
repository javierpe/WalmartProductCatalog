package com.javier.storage;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DynamicFacet extends RealmObject {
    private int attrGroupId;
    private String value;
    @SerializedName("attrOrder")
    private int order;
    @SerializedName("attrName")
    private String name;
    @SerializedName("attrDesc")
    private String description;
}
