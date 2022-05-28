/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kitti
 */
public class BookingDao {
    package flightmanagementsystem.file;

import flightmanagementsystem.model.BookingModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingFileOperations {

    public List<BookingModel> getBookings() {
        List<BookingModel> list = new ArrayList<>();
        File file = new File("booking.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] splits = line.split(",");
                BookingModel bookingModel = new BookingModel();
                bookingModel.setId(Long.parseLong(splits[0]));
                bookingModel.setFlightId(Long.parseLong(splits[1]));
                bookingModel.setUserId(Long.parseLong(splits[1]));
                list.add(bookingModel);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Booking.txt file not found");
        }
        return list;
    }

    public String writeBookings(List<BookingModel> bookings) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("booking.txt"));
            for (BookingModel booking : bookings) {
                String output = booking.getId() + "," + booking.getFlightId() + "," + booking.getUserId() + "\n";
                writer.write(output);
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error occurred while writing to Booking.txt file.");
        }
        return "Successfully written to file";
    }
}

