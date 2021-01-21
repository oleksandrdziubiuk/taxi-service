package com.internet.shop.controller.car;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Car;
import com.internet.shop.model.Manufacturer;
import com.internet.shop.service.CarService;
import com.internet.shop.service.ManufactureService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    private final ManufactureService manufactureService = (ManufactureService)
            injector.getInstance(ManufactureService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String model = req.getParameter("model");
        Long manufacturerId = Long.valueOf(req.getParameter("manufacturer"));
        Manufacturer manufacturer = manufactureService.get(manufacturerId);
        Car car = new Car(model, manufacturer);
        carService.create(car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
