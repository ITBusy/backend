package com.poly.service;

import com.poly.entity.Manufacturer;

import java.util.List;

public interface IManufacturerService {
    Manufacturer createManufacturer(Manufacturer manufacturer);

    Manufacturer findById(Long id);

    List<Manufacturer> findAllManufacturers();
}
