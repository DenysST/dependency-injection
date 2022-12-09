package com.company.service.impl;

import com.company.lib.Inject;
import com.company.model.Product;
import com.company.service.FileReaderService;
import com.company.service.ProductParser;
import com.company.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductParser productParser;
    @Inject
    private FileReaderService fileReaderService;

    @Override
    public List<Product> getAllFromFile(String filePath) {
        return fileReaderService.readFile(filePath)
                .stream()
                .map(productParser::parse)
                .collect(Collectors.toList());
    }
}