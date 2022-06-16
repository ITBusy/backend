package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pId;
    private String name;
    private String cpu;
    private String gpu;
    private String hardDiskDrive;
    private String ramdisk;
    private String monitors;
    private String batteryCapacity;
    private String warranty;
    @Column(name = "insights", length = 8000)
    private String insights;
    private String keyboardLights;
    private String design;
    private String sizeAndWeight;
    //    @Temporal(TemporalType.DATE)
    private Integer launchDate;
    private String operatingSystem;
    private String gateway;
    private Double price;
    private boolean active;
    private String imageMain;
    @ManyToOne(fetch = FetchType.EAGER)
    private Manufacturer manufacturer;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<ImageProducts> imageProductsSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<OrderDetails> orderDetailsSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<Comment> comment = new LinkedHashSet<>();

    public Product() {
    }

    public Product(Long pId, String name, String cpu, String gpu, String hardDiskDrive, String ramdisk, String monitors,
                   String batteryCapacity, String warranty, String insights, String keyboardLights, String design,
                   String sizeAndWeight, Integer launchDate, String operatingSystem, String gateway,
                   Double price, boolean active, String imageMain, Manufacturer manufacturer, Category category,
                   Set<ImageProducts> imageProductsSet, Set<OrderDetails> orderDetailsSet, Set<Comment> comment) {
        this.pId = pId;
        this.name = name;
        this.cpu = cpu;
        this.gpu = gpu;
        this.hardDiskDrive = hardDiskDrive;
        this.ramdisk = ramdisk;
        this.monitors = monitors;
        this.batteryCapacity = batteryCapacity;
        this.warranty = warranty;
        this.insights = insights;
        this.keyboardLights = keyboardLights;
        this.design = design;
        this.sizeAndWeight = sizeAndWeight;
        this.launchDate = launchDate;
        this.operatingSystem = operatingSystem;
        this.gateway = gateway;
        this.price = price;
        this.active = active;
        this.imageMain = imageMain;
        this.manufacturer = manufacturer;
        this.category = category;
        this.imageProductsSet = imageProductsSet;
        this.orderDetailsSet = orderDetailsSet;
        this.comment = comment;
    }

    public Long getPId() {
        return pId;
    }

    public void setPId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getHardDiskDrive() {
        return hardDiskDrive;
    }

    public void setHardDiskDrive(String hardDiskDrive) {
        this.hardDiskDrive = hardDiskDrive;
    }

    public String getRamdisk() {
        return ramdisk;
    }

    public void setRamdisk(String ramdisk) {
        this.ramdisk = ramdisk;
    }

    public String getMonitors() {
        return monitors;
    }

    public void setMonitors(String monitors) {
        this.monitors = monitors;
    }

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getInsights() {
        return insights;
    }

    public void setInsights(String insights) {
        this.insights = insights;
    }

    public String getKeyboardLights() {
        return keyboardLights;
    }

    public void setKeyboardLights(String keyboardLights) {
        this.keyboardLights = keyboardLights;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getSizeAndWeight() {
        return sizeAndWeight;
    }

    public void setSizeAndWeight(String sizeAndWeight) {
        this.sizeAndWeight = sizeAndWeight;
    }

    public Integer getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Integer launchDate) {
        this.launchDate = launchDate;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<ImageProducts> getImageProductsSet() {
        return imageProductsSet;
    }

    public void setImageProductsSet(Set<ImageProducts> imageProductsSet) {
        this.imageProductsSet = imageProductsSet;
    }

    public Set<OrderDetails> getOrderDetailsSet() {
        return orderDetailsSet;
    }

    public void setOrderDetailsSet(Set<OrderDetails> orderDetailsSet) {
        this.orderDetailsSet = orderDetailsSet;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageMain() {
        return imageMain;
    }

    public void setImageMain(String imageMain) {
        this.imageMain = imageMain;
    }
}
