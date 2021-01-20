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
        System.out.println(manufactureService.getAll());
        Manufacturer manufacturer = new Manufacturer("Opel", "Germany");
        manufactureService.create(manufacturer);
        System.out.println(manufactureService.delete(manufacturer.getId()));
        System.out.println(manufactureService.get(manufacturer.getId()));
        System.out.println(manufactureService.getAll());

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        Driver driverJohn = new Driver("John", "323");
        driverService.create(driverJohn);
        System.out.println(driverService.getAll());
        driverService.delete(driverJohn.getId());
        Driver driverBob = new Driver("Bob", "445");
        System.out.println(driverService.create(driverBob));

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car car = new Car("aaaa", manufactureService.get(manufacturer.getId()));
        carService.create(car);
        carService.addDriverToCar(driverService.get(driverBob.getId()), car);
        System.out.println(carService.getAll());
        System.out.println(carService.getAllByDriver(driverBob.getId()));
        carService.removeDriverFromCar(driverJohn, car);
        carService.delete(car.getId());
        System.out.println(carService.update(car));
        System.out.println(carService.get(car.getId()));
        System.out.println(carService.getAll());
        Car updated = carService.get(car.getId());
        updated.setModel("qqqw");
        System.out.println(carService.update(updated));
        Car carAudi = new Car("Q8", manufacturer);
        carService.create(carAudi);
        carService.addDriverToCar(driverBob, carAudi);
        System.out.println(carService.get(carAudi.getId()));
    }
}
