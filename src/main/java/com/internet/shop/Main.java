package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Manufacturer;
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
    }
}
