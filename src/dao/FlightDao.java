/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configuration.DatabaseConfiguration;
import model.FlightModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kitti
 */
public class FlightDao {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final String SELECT_FLIGHT_BASED_ON_ID_QUERY = "select id, flightName, fromLocation, toLocation, date, seatsAvailable from flight where id = ?";
    private final String SELECT_FLIGHT_BASED_ON_FILTER = "select id, flightName, fromLocation, toLocation, date, seatsAvailable from flight where fromLocation = ? and toLocation = ? and date = ?";
    private final String DELETE_FLIGHT_QUERY = "delete from flight where id = ?";
    private final String INSERT_FLIGHT_QUERY = "insert into flight (id, flightName, fromLocation, toLocation, date, seatsAvailable) values (?,?,?,?,?,?)";

    public void insertFlight(FlightModel flight) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(INSERT_FLIGHT_QUERY)) {
            int index = 0;
            statement.setLong(++index, flight.getId());
            statement.setString(++index, flight.getFlightName());
            statement.setString(++index, flight.getFromLocation());
            statement.setString(++index, flight.getToLocation());
            statement.setString(++index, flight.getDate().format(formatter));
            statement.setInt(++index, flight.getSeatsAvailable());
            statement.executeUpdate();
        }
    }

    public boolean deleteFlight(long userId) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(DELETE_FLIGHT_QUERY)) {
            statement.setLong(1, userId);
            return statement.executeUpdate() > 0;
        }
    }

    public FlightModel getFlightById(long flightId) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_FLIGHT_BASED_ON_ID_QUERY)) {
            statement.setLong(1, flightId);
            System.out.println("Executing Query: " + statement);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return getFlight(results);
            }
            return null;
        }
    }

    public List<FlightModel> getFlightByFilter(String fromLocation, String toLocation, String date) throws SQLException {
        List<FlightModel> flightModelList = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_FLIGHT_BASED_ON_FILTER)) {
            statement.setString(1, fromLocation);
            statement.setString(2, toLocation);
            statement.setString(3, date);
            System.out.println("Executing Query: " + statement);

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                flightModelList.add(getFlight(results));
            }
            return flightModelList;
        }
    }

    private FlightModel getFlight(ResultSet results) throws SQLException {
        FlightModel flightModel = new FlightModel();
        flightModel.setId(results.getLong("id"));
        flightModel.setFlightName(results.getString("flightName"));
        flightModel.setFromLocation(results.getString("fromLocation"));
        flightModel.setToLocation(results.getString("toLocation"));
        flightModel.setDate(LocalDate.parse(results.getString("date"), formatter));
        flightModel.setSeatsAvailable(results.getInt("seatsAvailable"));
        return flightModel;
    }
}
