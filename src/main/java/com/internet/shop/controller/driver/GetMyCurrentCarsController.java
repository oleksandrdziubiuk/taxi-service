package com.internet.shop.controller.driver;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Car;
import com.internet.shop.service.CarService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMyCurrentCarsController extends HttpServlet {
    private static final String DRIVER_ID = "id";
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final CarService carService = (CarService)
            injector.getInstance(CarService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long driverId = (Long) req.getSession().getAttribute(DRIVER_ID);
        List<Car> driverList = carService.getAllByDriver(driverId);
        req.setAttribute("cars", driverList);
        req.getRequestDispatcher("/WEB-INF/views/cars/drivers/my.jsp").forward(req, resp);
    }
}
