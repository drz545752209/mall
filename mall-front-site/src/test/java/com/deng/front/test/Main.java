package com.deng.front.test;

import com.deng.mall.domain.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products=new ArrayList<>();
        Product product=new Product();
        product.setPrice(200);
        product.setName("sdasd");
        Product product1=new Product();
        product1.setPrice(100);
        product1.setName("sdasd23");
        Product product2=new Product();
        product2.setPrice(300);
        product2.setName("sdasd444");

        products.add(product);
        products.add(product1);
        products.add(product2);

        Comparator<Product> priceComparator=(o1, o2) -> o1.getPrice()-o2.getPrice();
        products.sort(priceComparator);

        products.forEach(System.out::println);

    }

}
