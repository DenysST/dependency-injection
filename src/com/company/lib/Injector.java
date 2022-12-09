package com.company.lib;

import com.company.service.FileReaderService;
import com.company.service.ProductParser;
import com.company.service.ProductService;
import com.company.service.impl.FileReaderServiceImpl;
import com.company.service.impl.ProductParserImpl;
import com.company.service.impl.ProductServiceImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Injector {
    private static final Injector injector = new Injector();
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public static Injector getInjector() {
        return injector;
    }

    public Object getInstance(Class<?> interfaceOrClass) {
        Object classInstance = null;
        Class<?> clazz = getImplementation(interfaceOrClass);
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object filedInstance = getInstance(field.getType());
                classInstance = createNewInstance(clazz);
                field.setAccessible(true);
                try {
                    field.set(classInstance, filedInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(String.format("Can't initialize Object. Class: %s, Field: %s",
                            clazz.getName(), field.getName()));
                }
            }
        }
        if (classInstance == null) {
            classInstance = createNewInstance(clazz);
        }
        return classInstance;
    }

    private Object createNewInstance(Class<?> clazz) {
        if (instances.containsKey(clazz)) {
            return instances.get(clazz);
        }
        try {
            Object instance = clazz.getConstructor().newInstance();
            instances.put(clazz, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Can't create instance of " + clazz.getName());
        }
    }

    private Class<?> getImplementation(Class<?> clazz) {
        Map<Class<?>, Class<?>> implementations = new HashMap<>();
        implementations.put(ProductService.class, ProductServiceImpl.class);
        implementations.put(ProductParser.class, ProductParserImpl.class);
        implementations.put(FileReaderService.class, FileReaderServiceImpl.class);
        if (clazz.isInterface()) {
            return implementations.get(clazz);
        }
        return clazz;
    }
}