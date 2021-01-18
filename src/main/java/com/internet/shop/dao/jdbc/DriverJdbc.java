package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.DriverDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Driver;
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
public class DriverJdbc implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String query = "INSERT INTO drivers (drivers_name, license_number) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenceNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create " + driver + " in DB", e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String query = "SELECT * FROM drivers WHERE drivers_id=? AND deleted = false";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                driver = createDriver(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver id + " + id + " from DB", e);
        }
        return Optional.ofNullable(driver);
    }

    @Override
    public Driver update(Driver driver) {
        String query = "UPDATE drivers SET drivers_name = ?, license_number = ? "
                + "WHERE drivers_id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenceNumber());
            statement.setLong(3, driver.getId());
            statement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update " + driver + " in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE drivers SET deleted = ?  WHERE drivers_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, true);
            statement.setLong(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer id "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM drivers WHERE deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Driver> allDrivers = new ArrayList<>();
            while (resultSet.next()) {
                allDrivers.add(createDriver(resultSet));
            }
            return allDrivers;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t get all drivers from DB ", e);
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
}
