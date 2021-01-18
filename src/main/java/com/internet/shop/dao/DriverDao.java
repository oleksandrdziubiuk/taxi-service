package com.internet.shop.dao;

import com.internet.shop.model.Driver;
import java.util.List;
import java.util.Optional;

public interface DriverDao extends GenericDao<Driver, Long> {
    Driver create(Driver driver);

    Optional<Driver> get(Long id);

    List<Driver> getAll();

    Driver update(Driver car);

    boolean delete(Long id);
}
