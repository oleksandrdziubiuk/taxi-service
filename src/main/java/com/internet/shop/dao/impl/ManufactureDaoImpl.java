package com.internet.shop.dao.impl;

import com.internet.shop.dao.ManufactureDao;
import com.internet.shop.db.Storage;
import com.internet.shop.model.Manufacturer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class ManufactureDaoImpl implements ManufactureDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Storage.manufacturers.stream()
                .filter(m -> Objects.equals(m.getId(), id))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        IntStream.range(0, Storage.manufacturers.size())
                .filter(i -> Storage.manufacturers.get(i).getId().equals(manufacturer.getId()))
                .forEach(i -> Storage.manufacturers.set(i, manufacturer));
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.removeIf(m -> m.getId().equals(id));
    }
}
