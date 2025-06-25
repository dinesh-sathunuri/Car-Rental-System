package com.carrental.whybuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarImageDto {
    private Long id;
    private String base64ImageData;
    // No rentalCar field here to avoid circular references
}
