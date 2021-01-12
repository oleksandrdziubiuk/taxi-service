package com.internet.shop.service;

import com.internet.shop.model.Manufacturer;
import java.util.List;

public interface ManufactureService {
    Manufacturer create(Manufacturer manufacturer);

    Manufacturer get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
