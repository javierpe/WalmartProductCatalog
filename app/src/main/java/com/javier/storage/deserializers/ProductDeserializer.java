package com.javier.storage.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.javier.storage.DynamicFacet;
import com.javier.storage.OfferItem;
import com.javier.storage.PriceInfo;
import com.javier.storage.Product;

import java.lang.reflect.Type;
import java.util.Iterator;

import io.realm.RealmList;

public class ProductDeserializer implements JsonDeserializer<Product> {
    @Override
    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Product product = new Product();

        JsonObject productObj = json.getAsJsonObject().get("product").getAsJsonObject();
        product.setTitle(productObj.get("metaTitle").getAsString());
        product.setMetaDescription(productObj.get("metaDescription").getAsString());
        product.setLongDescription(productObj.get("longDescription").getAsString());
        product.setBrand(productObj.get("brand").getAsString());
        product.setDescription(productObj.get("description").getAsString());
        product.setDisplayName(productObj.get("displayName").getAsString());
        product.setLargeImageUrl(productObj.get("largeImageUrl").getAsString());
        product.setThumbnailImageUrl(productObj.get("thumbnailImageUrl").getAsString());
        product.setMaxMSI(productObj.get("maxMSI").getAsInt());

        JsonObject childSKUs = productObj.getAsJsonArray("childSKUs").get(0).getAsJsonObject();
        product.setId(childSKUs.get("id").getAsString());
        product.setRightLargeImageUrl(childSKUs.get("rightLargeImageUrl").getAsString());
        product.setLeftLargeImageUrl(childSKUs.get("leftLargeImageUrl").getAsString());
        product.setSuperiorLargeImageUrl(childSKUs.get("superiorLargeImageUrl").getAsString());
        product.setSeoDescription(childSKUs.get("seoDescription").getAsString());

        JsonElement dynamicFacetsElements = childSKUs.getAsJsonObject("dynamicFacets");
        Iterator<String> keys = ((JsonObject) dynamicFacetsElements).keySet().iterator();
        RealmList<DynamicFacet> dynamicFacets = new RealmList<>();
        while (keys.hasNext()){
            JsonObject facetJSON = ((JsonObject) dynamicFacetsElements).get(keys.next()).getAsJsonObject();
            DynamicFacet facet = new DynamicFacet();
            facet.setAttrGroupId(facetJSON.get("attrGroupId").getAsInt());
            facet.setValue(facetJSON.get("value").getAsString());
            facet.setOrder(facetJSON.get("attrOrder").getAsInt());
            facet.setName(facetJSON.get("attrName").getAsString());
            facet.setDescription(facetJSON.get("attrDesc").getAsString());
            dynamicFacets.add(facet);
        }

        product.setDynamicFacets(dynamicFacets);

        Iterator<JsonElement> offerListElements = childSKUs.getAsJsonArray("offerList").iterator();
        RealmList<OfferItem> offerItems = new RealmList<>();
        while (offerListElements.hasNext()){
            JsonObject offerJSON = offerListElements.next().getAsJsonObject();
            OfferItem offerItem = new OfferItem();
            offerItem.setSellerName(offerJSON.get("sellerName").getAsString());
            offerItem.setInvAvailable(offerJSON.get("isInvAvailable").getAsBoolean());
            offerItem.setOfferRank(offerJSON.get("offerRank").getAsInt());
            offerItem.setWarrantyInMonths(offerJSON.get("warrantyInMonths").getAsInt());
            offerItem.setOfferType(offerJSON.get("offerType").getAsString());
            offerItem.setRefurbished(offerJSON.get("refurbished").getAsBoolean());
            offerItem.setWarranty(offerJSON.get("warranty").getAsString());
            offerItem.setWarrantyCondition(offerJSON.get("warrantyCondition").getAsString());

            JsonObject priceInfoElements = offerJSON.get("priceInfo").getAsJsonObject();
            PriceInfo priceInfo = new PriceInfo();
            priceInfo.setOriginalPrice(priceInfoElements.get("originalPrice").getAsFloat());
            priceInfo.setSpecialPrice(priceInfoElements.get("specialPrice").getAsFloat());

            offerItem.setPriceInfo(priceInfo);
            offerItems.add(offerItem);
        }

        product.setOfferItems(offerItems);

        return product;
    }
}
