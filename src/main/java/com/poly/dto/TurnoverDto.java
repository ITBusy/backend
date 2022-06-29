package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoverDto implements Serializable {
    private String name;
    private Double price;
    private Integer quantity;
    private Integer month;
    private Integer year;
    private Double total;
}
