package com.carrental.whybuy.Mapping;

import com.carrental.whybuy.dto.CarImageDto;
import com.carrental.whybuy.entity.CarImage;
import java.util.Base64;

public class CarImageMapper {

    // Convert Entity to DTO (byte[] -> base64 String)
    public static CarImageDto toDto(CarImage entity) {
        if (entity == null) return null;

        CarImageDto dto = new CarImageDto();
        dto.setId(entity.getId());

        byte[] imageData = entity.getImageData();
        if (imageData != null) {
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            dto.setBase64ImageData(base64Image);
        }

        return dto;
    }

    // Convert DTO to Entity (base64 String -> byte[])
    public static CarImage toEntity(CarImageDto dto) {
        if (dto == null) return null;

        CarImage entity = new CarImage();
        entity.setId(dto.getId());

        String base64Image = dto.getBase64ImageData();
        if (base64Image != null) {
            byte[] imageData = Base64.getDecoder().decode(base64Image);
            entity.setImageData(imageData);
        }

        return entity;
    }
}
