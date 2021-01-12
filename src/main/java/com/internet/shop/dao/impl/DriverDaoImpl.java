package com.internet.shop.dao.impl;

import com.internet.shop.dao.DriverDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Driver;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Storage.drivers.stream()
                .filter(d -> Objects.equals(d.getId(), id))
                .findFirst();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(d -> Storage.drivers.get(d).getId().equals(driver.getId()))
                .forEach(d -> Storage.drivers.set(d, driver));
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers.removeIf(d -> d.getId().equals(id));
    }
}
