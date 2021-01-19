package com.internet.shop.dao;

import com.internet.shop.model.Car;
import java.util.List;

public interface CarDao extends GenericDao<Car, Long> {
    List<Car> getAllByDriver(Long driverId);
}
