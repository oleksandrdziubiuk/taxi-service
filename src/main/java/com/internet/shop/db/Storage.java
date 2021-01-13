package com.internet.shop.db;

import com.internet.shop.model.Car;
import com.internet.shop.model.Driver;
import com.internet.shop.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Car> cars = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static Long carId = 0L;
    public static Long driverId = 0L;
    public static Long manufacturerId = 0L;

    public static void addCar(Car car) {
        car.setId(++carId);
        cars.add(car);
    }

    public static void addDriver(Driver driver) {
        driver.setId(++driverId);
        drivers.add(driver);
    }

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturers.add(manufacturer);
    }
}
