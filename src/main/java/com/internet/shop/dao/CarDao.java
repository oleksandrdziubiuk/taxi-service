package com.internet.shop.dao;

import com.internet.shop.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarDao extends GenericDao<Car, Long> {
    Car create(Car car);

    Optional<Car> get(Long id);

    List<Car> getAll();

    Car update(Car car);

    boolean delete(Long id);

    public List<Car> getAllByDriver(Long driverId);
}
