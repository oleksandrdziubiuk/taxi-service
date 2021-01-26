package com.internet.shop.security;

import com.internet.shop.exception.AuthenticationException;
import com.internet.shop.model.Driver;

public interface AuthenticationService {
    Driver login(String login, String password) throws AuthenticationException;
}
