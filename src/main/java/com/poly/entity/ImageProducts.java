package com.poly.entity;

import javax.persistence.*;

@Entity
public class ImageProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Ip_id;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    public ImageProducts() {
    }

    public ImageProducts(Long ip_id, String imageUrl, Product product) {
        Ip_id = ip_id;
        this.imageUrl = imageUrl;
        this.product = product;
    }

    public Long getIp_id() {
        return Ip_id;
    }

    public void setIp_id(Long ip_id) {
        Ip_id = ip_id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
