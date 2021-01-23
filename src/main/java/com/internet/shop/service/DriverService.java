package com.internet.shop.service;

import com.internet.shop.model.Driver;
import java.util.Optional;

public interface DriverService extends GenericService<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
