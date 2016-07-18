package com.cnu2016;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Piyush on 7/10/16.
 * POJO for post order api. Add product to card
 */

public class ProductSerializer {
    private int productId;
    private int quantity;

    @JsonProperty("product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    @JsonProperty("qty")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    ProductSerializer()
    {

    }
    ProductSerializer(int productId, int qunatity)
    {
        this.productId = productId;
        this.quantity = qunatity;
    }
}
