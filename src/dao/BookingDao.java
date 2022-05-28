/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configuration.DatabaseConfiguration;
import model.BookingModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author kitti
 */
public class BookingDao {
   

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final String SELECT_BOOKING_BASED_ON_ID_QUERY = "select id, flightId, userId from booking where userId = ?";
    private final String DELETE_BOOKING_QUERY = "delete from booking where id = ?";
    private final String INSERT_BOOKING_QUERY = "insert into booking (id, flightId, userId) values (?,?,?)";

    public void book(BookingModel bookingModel) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(INSERT_BOOKING_QUERY)) {
            int index = 0;
            statement.setLong(++index, bookingModel.getId());
            statement.setLong(++index, bookingModel.getFlightId());
            statement.setLong(++index, bookingModel.getUserId());
            statement.executeUpdate();
        }
    }

    public boolean unbook(long bookingId) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(DELETE_BOOKING_QUERY)) {
            statement.setLong(1, bookingId);
            return statement.executeUpdate() > 0;
        }
    }

    public List<BookingModel> getMyAllBookings(long userId) throws SQLException {
        List<BookingModel> bookingModels = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_BOOKING_BASED_ON_ID_QUERY)) {
            statement.setLong(1, userId);
            System.out.println("Executing Query: " + statement);

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                bookingModels.add(getBooking(results));
            }
            return bookingModels;
        }
    }

    private BookingModel getBooking(ResultSet results) throws SQLException {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(results.getInt("id"));
        bookingModel.setFlightId(results.getInt("flightId"));
        bookingModel.setUserId(results.getInt("userId"));
        return bookingModel;
    }
}