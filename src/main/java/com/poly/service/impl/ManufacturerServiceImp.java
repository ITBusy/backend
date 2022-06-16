package com.poly.service.impl;

import com.poly.entity.Manufacturer;
import com.poly.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImp implements com.poly.service.IManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Manufacturer> findAllManufacturers() {
        return this.manufacturerRepository.findAll();
    }
}
