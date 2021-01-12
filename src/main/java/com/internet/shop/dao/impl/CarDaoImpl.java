package com.internet.shop.dao.impl;

import com.internet.shop.dao.CarDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Car;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.cars.stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(c -> Storage.cars.get(c).getId().equals(car.getId()))
                .forEach(c -> Storage.cars.set(c, car));
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(c -> c.getId().equals(id));
    }
}
