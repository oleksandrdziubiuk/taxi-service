package com.internet.shop.controller.manufacturer;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Manufacturer;
import com.internet.shop.service.ManufactureService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final ManufactureService manufactureService = (ManufactureService)
            injector.getInstance(ManufactureService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/manufacturers/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufactureService.create(manufacturer);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
