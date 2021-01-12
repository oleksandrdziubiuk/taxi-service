package com.internet.shop.service.impl;

import com.internet.shop.dao.CarDao;
import com.internet.shop.dao.DriverDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Car;
import com.internet.shop.model.Driver;
import com.internet.shop.service.CarService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Inject
    private DriverDao driverDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id).orElse(null);
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        if (!driverDao.getAll().contains(driver)) {
            driverDao.create(driver);
        }
        car.getDrivers().add(driver);
        carDao.update(car);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        car.getDrivers().remove(driver);
        carDao.update(car);
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAll().stream()
                .filter(c -> c.getDrivers()
                        .stream()
                        .map(Driver::getId)
                        .collect(Collectors.toList())
                        .contains(driverId))
                .collect(Collectors.toList());
    }
}
