package com.example.accident_hotspot;

public class VehicleModel {

    public String model;
    public String license;
    public String vehicleType;
    public String fuelType;
    public String safetyRating;
    public String lastService;

    public VehicleModel(String model, String license, String vehicleType,
                        String fuelType, String safetyRating, String lastService) {
        this.model = model;
        this.license = license;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.safetyRating = safetyRating;
        this.lastService = lastService;
    }
}
