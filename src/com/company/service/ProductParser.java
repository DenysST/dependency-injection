package com.company.service;

import com.company.model.Product;

public interface ProductParser {
    /**
     * Parse the productInfo and return created {@link Product} object.
     * The `productInfo` could be received from any source.
     * For example from file with all products stored in the shop.
     * @param productInfo - the information about a product
     * @return newly create {@link Product} object based on the `productInfo` input.
     */
    Product parse(String productInfo);
}