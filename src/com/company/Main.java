package com.company;

import com.company.lib.Injector;
import com.company.model.Product;
import com.company.service.ProductService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInjector();
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        List<Product> products = productService.getAllFromFile("products.txt");
        products.forEach(System.out::println);
    }
}
