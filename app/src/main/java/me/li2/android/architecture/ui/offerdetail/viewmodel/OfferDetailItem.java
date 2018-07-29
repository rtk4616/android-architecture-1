package me.li2.android.architecture.ui.offerdetail.viewmodel;

import io.reactivex.functions.Action;

/**
 * Created by weiyi on 29/7/18.
 * https://github.com/li2
 */
public class OfferDetailItem {

    public String photoCloudinaryId;

    public String name;

    public String description;

    public String location;

    public String minPrice;

    public String maxPrice;

    public Action onShareAction;

    public OfferDetailItem(String photoCloudinaryId,
                           String name,
                           String description,
                           String location,
                           String minPrice,
                           String maxPrice,
                           Action onShareAction) {
        this.photoCloudinaryId = photoCloudinaryId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.onShareAction = onShareAction;
    }
}
