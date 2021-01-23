package com.internet.shop.controller.driver;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Driver;
import com.internet.shop.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final DriverService driverService = (DriverService)
            injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/drivers/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String licenceNumber = req.getParameter("licenceNumber");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Driver driver = new Driver(name, licenceNumber);
        driver.setLogin(login);
        driver.setPassword(password);
        driverService.create(driver);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
