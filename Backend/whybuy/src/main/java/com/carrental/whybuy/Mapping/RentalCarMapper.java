package com.carrental.whybuy.Mapping;

import com.carrental.whybuy.dto.CarImageDto;
import com.carrental.whybuy.dto.RentalCarDto;
import com.carrental.whybuy.dto.RentalDto;
import com.carrental.whybuy.entity.CarImage;
import com.carrental.whybuy.entity.Rental;
import com.carrental.whybuy.entity.RentalCar;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class RentalCarMapper {

    // Entity → DTO
    public static RentalCarDto toDto(RentalCar rentalCar) {
        if (rentalCar == null) {
            return null;
        }

        RentalCarDto dto = new RentalCarDto();
        dto.setId(rentalCar.getId());
        dto.setMake(rentalCar.getMake());
        dto.setModel(rentalCar.getModel());
        dto.setYear(rentalCar.getYear());
        dto.setLicensePlate(rentalCar.getLicensePlate());
        dto.setDescription(rentalCar.getDescription());
        dto.setCreatedAt(rentalCar.getCreatedAt());
        dto.setRentPerDay(rentalCar.getRentPerDay());

        if (rentalCar.getCustomer() != null) {
            dto.setCustomerId(rentalCar.getCustomer().getId());
        }

        if (rentalCar.getRentals() != null) {
            List<RentalDto> rentalDtos = rentalCar.getRentals().stream()
                    .map(RentalMapper::toDto)
                    .collect(Collectors.toList());
            dto.setRentals(rentalDtos);
        }

        if (rentalCar.getImages() != null) {
            List<CarImageDto> carImageDtos = rentalCar.getImages().stream()
                    .map(RentalCarMapper::carImageToDto)
                    .collect(Collectors.toList());
            dto.setImages(carImageDtos);
        }

        return dto;
    }

    // DTO → Entity
    public static RentalCar toEntity(RentalCarDto dto) {
        if (dto == null) {
            return null;
        }

        RentalCar rentalCar = new RentalCar();
        rentalCar.setId(dto.getId());
        rentalCar.setMake(dto.getMake());
        rentalCar.setModel(dto.getModel());
        rentalCar.setYear(dto.getYear());
        rentalCar.setLicensePlate(dto.getLicensePlate());
        rentalCar.setDescription(dto.getDescription());
        rentalCar.setRentPerDay(dto.getRentPerDay());

        if (dto.getCustomerId() != null) {
            var customer = new com.carrental.whybuy.entity.Customer();
            customer.setId(dto.getCustomerId());
            rentalCar.setCustomer(customer);
        }

        if (dto.getRentals() != null) {
            List<Rental> rentals = dto.getRentals().stream()
                    .map(RentalMapper::toEntity)
                    .collect(Collectors.toList());
            rentalCar.setRentals(rentals);
        }

        if (dto.getImages() != null) {
            List<CarImage> images = dto.getImages().stream()
                    .map(RentalCarMapper::carImageToEntity)
                    .peek(image -> image.setRentalCar(rentalCar))  // set parent reference
                    .collect(Collectors.toList());
            rentalCar.setImages(images);
        }

        return rentalCar;
    }

    // Helper method: Entity → DTO for CarImage
    private static CarImageDto carImageToDto(CarImage entity) {
        if (entity == null) return null;

        CarImageDto dto = new CarImageDto();
        dto.setId(entity.getId());

        // Convert byte[] imageData to Base64 String for JSON
        if (entity.getImageData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(entity.getImageData());
            dto.setBase64ImageData(base64Image);
        }

        return dto;
    }

    // Helper method: DTO → Entity for CarImage
    private static CarImage carImageToEntity(CarImageDto dto) {
        if (dto == null) return null;

        CarImage entity = new CarImage();
        entity.setId(dto.getId());

        // Decode Base64 String back to byte[]
        if (dto.getBase64ImageData() != null) {
            byte[] imageData = Base64.getDecoder().decode(dto.getBase64ImageData());
            entity.setImageData(imageData);
        }

        return entity;
    }
}
