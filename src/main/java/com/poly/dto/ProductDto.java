package com.poly.dto;

import com.poly.entity.Category;
import com.poly.entity.ImageProducts;
import com.poly.entity.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long pId;
    private String name;
    private String cpu;
    private String gpu;
    private String hardDiskDrive;
    private String ramdisk;
    private String monitors;
    private String batteryCapacity;
    private String warranty;
    private String insights;
    private String keyboardLights;
    private String design;
    private String sizeAndWeight;
    private Integer launchDate;
    private String operatingSystem;
    private String gateway;
    private Double price;
    private boolean active;
    private String imageMain;
    private Manufacturer manufacturer;
    private Category category;
    private List<ImageProducts> imageUrl;
}
