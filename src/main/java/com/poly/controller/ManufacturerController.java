package com.poly.controller;

import com.poly.dto.ResponseObject;
import com.poly.entity.Manufacturer;
import com.poly.service.impl.ManufacturerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturer")
@CrossOrigin("*")
public class ManufacturerController {
    @Autowired
    private ManufacturerServiceImp manufacturerServiceImp;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> findAllManufacturers() {
        List<Manufacturer> manufacturer = this.manufacturerServiceImp.findAllManufacturers();
        return ResponseEntity.ok(new ResponseObject("OK", "Have data", manufacturer));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createManufacturer(@RequestBody Manufacturer manufacturer) {
        ResponseEntity<ResponseObject> message = null;
        manufacturer.setName(manufacturer.getName().toUpperCase());
        Manufacturer manufacturer1 = this.manufacturerServiceImp.createManufacturer(manufacturer);
        if (manufacturer1 != null) {
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Create manufacturer is success", manufacturer1)
            );
        } else {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Create manufacturer is failed", null)
            );
        }
        return message;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Manufacturer manufacturer = this.manufacturerServiceImp.findById(id);
        if (manufacturer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not Found", "Don't found data with id= " + id, null)
            );
        } else {
            return ResponseEntity.ok(new ResponseObject("Found", "Found data with id= " + id, manufacturer));
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponseObject> updateManufacturer(@RequestBody Manufacturer manufacturer) {
        ResponseEntity<ResponseObject> message;
        manufacturer.setName(manufacturer.getName().toUpperCase());
        Manufacturer manufacturer1 = this.manufacturerServiceImp.createManufacturer(manufacturer);
        if (manufacturer1 != null) {
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Update manufacturer is success", manufacturer1)
            );
        } else {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Update manufacturer is failed", null)
            );
        }
        return message;
    }
}
