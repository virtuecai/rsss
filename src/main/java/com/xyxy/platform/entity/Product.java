package com.xyxy.platform.entity;

/**
 * Created by Administrator on 2015/11/8 0008.
 */
public class Product {

    private Product1 product1;
    private Product2 product2;

    public Product(Product1 product1, Product2 product2) {
        this.product1 = product1;
        this.product2 = product2;
    }

    public Product1 getProduct1() {
        return product1;
    }

    public void setProduct1(Product1 product1) {
        this.product1 = product1;
    }

    public Product2 getProduct2() {
        return product2;
    }

    public void setProduct2(Product2 product2) {
        this.product2 = product2;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product1=" + product1 +
                ", product2=" + product2 +
                '}';
    }
}
