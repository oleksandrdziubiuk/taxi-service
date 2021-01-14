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
        System.out.println(manufactureService.delete(26L));
        System.out.println(manufactureService.get(27L));
        System.out.println(manufactureService.getAll());


    }
}
