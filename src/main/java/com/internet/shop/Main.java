package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Car;
import com.internet.shop.model.Driver;
import com.internet.shop.model.Manufacturer;
import com.internet.shop.service.CarService;
import com.internet.shop.service.DriverService;
import com.internet.shop.service.ManufactureService;

public class Main {
    private static final Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ManufactureService manufactureService =
                (ManufactureService) injector.getInstance(ManufactureService.class);
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        Manufacturer manufacturerSkoda = new Manufacturer("Skoda", "Czech Republic");
        manufactureService.create(manufacturerAudi);
        manufactureService.create(manufacturerSkoda);
        System.out.println(manufactureService.getAll());
        System.out.println(manufactureService.get(2L));
        Manufacturer updateAudi = manufactureService.get(1L);
        updateAudi.setName("BMW");
        updateAudi.setCountry("Italy");
        System.out.println(manufactureService.update(updateAudi));
        manufactureService.delete(1L);
        System.out.println(manufactureService.getAll());

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carAudi = new Car("q8", manufacturerAudi);
        Car carSkoda = new Car("a8", manufacturerSkoda);
        System.out.println(carService.create(carAudi));
        System.out.println(carService.create(carSkoda));

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        Driver driverJohn = new Driver("John", "123");
        Driver driverBob = new Driver("Bob", "321");

        driverService.create(driverBob);
        driverService.create(driverJohn);
        System.out.println(driverService.getAll());
        System.out.println(driverService.get(2L));

        carService.addDriverToCar(driverBob, carAudi);
        carService.addDriverToCar(driverJohn, carSkoda);
        System.out.println(carService.getAll());

        System.out.println(carService.getAllByDriver(1L));
        carService.removeDriverFromCar(driverBob, carAudi);
        System.out.println(carService.getAll());
    }
}
