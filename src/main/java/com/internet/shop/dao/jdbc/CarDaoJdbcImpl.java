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
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String createQuery = "INSERT INTO cars (model, manufacturer_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert this car:" + car + "into DB ", e);
        }
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        String getQuery = "SELECT c.id, c.model, m.manufacture_id, "
                + "m.manufacture_name, m.manufacture_country "
                + "FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacture_id "
                + "WHERE c.id = ? AND c.deleted = FALSE;";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = createCar(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get data with id:"
                    + id + " from DB ", e);
        }
        if (car != null) {
            car.setDrivers(getDrivers(car.getId()));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars SET manufacturer_id = ?, model = ? "
                + "WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateCars = connection.prepareStatement(updateQuery)) {
            updateCars.setLong(1, car.getManufacturer().getId());
            updateCars.setString(2, car.getModel());
            updateCars.setLong(3, car.getId());
            updateCars.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("We can`t update this car:" + car, e);
        }
        deleteDriversFromCar(car);
        addDriversToCar(car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE cars SET deleted = TRUE  WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete data with id:"
                    + id + " from DB ", e);
        }
    }

    @Override
    public List<Car> getAll() {
        String getAllQuery = "SELECT c.id, c.model, m.manufacture_id, "
                + "m.manufacture_name, m.manufacture_country "
                + "FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacture_id "
                + "WHERE c.deleted = FALSE;";
        List<Car> allCars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = statement.executeQuery(getAllQuery);
            while (resultSet.next()) {
                allCars.add(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get all data from DB ", e);
        }
        for (Car car : allCars) {
            car.setDrivers(getDrivers(car.getId()));
        }
        return allCars;
    }

    @Override
    public List<Car> getAllByDriver(Long id) {
        String getAllByDriverQuery = "SELECT c.id, c.model, "
                + "m.manufacture_id, m.manufacture_name, m.manufacture_country "
                + "FROM cars c "
                + "JOIN drivers_cars d_c ON c.id = d_c.car_id "
                + "JOIN drivers d ON c.id = d_c.car_id "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacture_id "
                + "WHERE d.id = ? AND d.deleted = FALSE AND c.deleted = FALSE;";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getAllByDriverQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("We can't get data for this driver:"
                    + id, e);
        }
        for (Car car : cars) {
            car.setDrivers(getDrivers(car.getId()));
        }
        return cars;
    }

    private List<Driver> getDrivers(Long carId) {
        String getDriverQuery = "SELECT d.name, d.license_number, d.id "
                + "FROM drivers_cars dc "
                + "JOIN drivers d ON dc.driver_id = d.id "
                + "WHERE dc.car_id = ? AND d.deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getDriverQuery)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(createDriver(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get drivers for this car", e);
        }
    }

    private Driver createDriver(ResultSet resultSet) {
        try {
            Long driverId = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String license = resultSet.getString("license_number");
            Driver driver = new Driver(name, license);
            driver.setId(driverId);
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse driver from ResultSet", e);
        }
    }

    private Car createCar(ResultSet resultSet) {
        try {
            Long carId = resultSet.getObject("id", Long.class);
            String model = resultSet.getString("model");
            Manufacturer manufacturer = createManufacturer(resultSet);
            Car car = new Car(model, manufacturer);
            car.setId(carId);
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse car from ResultSet", e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            Long manufacturerId = resultSet.getObject("manufacture_id", Long.class);
            String name = resultSet.getString("manufacture_name");
            String country = resultSet.getString("manufacture_country");
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(manufacturerId);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse manufacture from ResultSet", e);
        }
    }

    private void addDriversToCar(Car car) {
        String insertQuery = "INSERT INTO drivers_cars(driver_id, car_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            for (Driver driver : car.getDrivers()) {
                statement.setLong(1, driver.getId());
                statement.setLong(2, car.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add driver " + car, e);
        }
    }

    private void deleteDriversFromCar(Car car) {
        String deleteQuery = "DELETE FROM drivers_cars WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver from car " + car, e);
        }
    }
}
