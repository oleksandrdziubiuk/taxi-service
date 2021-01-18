package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.CarDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Car;
import com.internet.shop.model.Driver;
import com.internet.shop.model.Manufacturer;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarJdbc implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars (cars_model, manufacturer_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert this car:" + car + "into DB ", e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT d.drivers_id, d.drivers_name, d.license_number, "
                + "c.cars_id, c.cars_model, "
                + "m.manufacture_id, m.manufacture_name, m.manufacture_country "
                + "FROM cars c "
                + "LEFT JOIN drivers_cars d_c ON c.cars_id = d_c.car_id "
                + "LEFT JOIN drivers d ON c.cars_id = d_c.car_id "
                + "LEFT JOIN manufacturers m ON c.manufacturer_id = m.manufacture_id "
                + "WHERE c.cars_id = ?";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                car = createCar(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get data with id:"
                    + id + " from DB ", e);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars SET manufacturer_id = ?, cars_model = ? "
                + "WHERE cars_id = ? AND deleted = false";
        String deleteQuery = "DELETE FROM drivers_cars WHERE car_id = ?";
        String insertQuery = "INSERT INTO drivers_cars(driver_id, car_id) VALUES (?, ?)";
        Long carId = car.getId();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateCars = connection.prepareStatement(updateQuery);
                PreparedStatement deleteDrivers =
                        connection.prepareStatement(deleteQuery);
                PreparedStatement insertDrivers =
                        connection.prepareStatement(insertQuery)) {
            updateCars.setLong(1, car.getManufacturer().getId());
            updateCars.setString(2, car.getModel());
            updateCars.setLong(3, carId);
            updateCars.executeUpdate();
            deleteDrivers.setLong(1, car.getId());
            deleteDrivers.executeUpdate();
            insertDrivers.setLong(2, car.getId());
            for (Driver driver : car.getDrivers()) {
                insertDrivers.setLong(1, driver.getId());
                insertDrivers.executeUpdate();
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("We can`t update this car:" + car, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET deleted = ?  WHERE cars_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, true);
            statement.setLong(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete data with id:"
                    + id + " from DB ", e);
        }
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT d.drivers_id, d.drivers_name, d.license_number, "
                + "c.cars_id, c.cars_model, "
                + "m.manufacture_id, m.manufacture_name, m.manufacture_country "
                + "FROM cars c "
                + "LEFT JOIN drivers_cars d_c ON c.cars_id = d_c.car_id "
                + "LEFT JOIN drivers d ON c.cars_id = d_c.car_id "
                + "LEFT JOIN manufacturers m ON c.manufacturer_id = m.manufacture_id";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Car> allCars = new ArrayList<>();
            while (resultSet.next()) {
                allCars.add(createCar(resultSet));
            }
            return allCars;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get all data from DB ", e);
        }
    }

    public List<Car> getAllByDriver(Long id) {
        String query = "SELECT d.drivers_id, d.drivers_name, d.license_number, "
                + "c.cars_id, c.cars_model, "
                + "m.manufacture_id, m.manufacture_name, m.manufacture_country "
                + "FROM cars c "
                + "LEFT JOIN drivers_cars d_c ON c.cars_id = d_c.car_id "
                + "LEFT JOIN drivers d ON c.cars_id = d_c.car_id "
                + "LEFT JOIN manufacturers m ON c.manufacturer_id = m.manufacture_id "
                + "WHERE d.drivers_id = ? ORDER BY d.drivers_id";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(createCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("We can't get data for this driver:"
                    + id, e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("manufacture_name");
            String country = resultSet.getString("manufacture_country");
            Long id = resultSet.getObject("manufacture_id", long.class);
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(id);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse manufacturer from ResultSet", e);
        }
    }

    private Driver createDriver(ResultSet resultSet) {
        try {
            String name = resultSet.getString("drivers_name");
            String license = resultSet.getString("license_number");
            Long id = resultSet.getObject("drivers_id", long.class);
            Driver driver = new Driver(name, license);
            driver.setId(id);
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse driver from ResultSet", e);
        }
    }

    private Car createCar(ResultSet resultSet) {
        try {
            String name = resultSet.getString("cars_model");
            Car car = new Car(name, createManufacturer(resultSet));
            car.setId(resultSet.getObject("cars_id", Long.class));
            Driver driver = createDriver(resultSet);
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next() && driver.getId() != null) {
                drivers.add(createDriver(resultSet));
            }
            car.setDrivers(drivers);
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse car from ResultSet", e);
        }
    }
}
