package com.company.service;


import com.company.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllFromFile(String filePath);
}