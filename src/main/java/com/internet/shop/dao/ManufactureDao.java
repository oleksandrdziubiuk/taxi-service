package com.internet.shop.dao;

import com.internet.shop.model.Manufacturer;
import java.util.List;
import java.util.Optional;

public interface ManufactureDao {
    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
