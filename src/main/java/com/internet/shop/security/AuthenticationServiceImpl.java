package com.internet.shop.security;

import com.internet.shop.exception.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Driver;
import com.internet.shop.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driver = driverService.findByLogin(login).orElseThrow(()
                -> new AuthenticationException("Incorrect login or password"));
        if (driver.getPassword().equals(password)) {
            return driver;
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
