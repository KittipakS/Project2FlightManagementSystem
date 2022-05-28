/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kitti
 */
public class AddFlight {FlightDao flightDao = new FlightDao();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void start() {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Flight Management System - Add Flight");
        jFrame.setSize(400, 500);

        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(40, 25, 40, 25));
        GridLayout gridLayout = new GridLayout(7, 2);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);

        jPanel.setLayout(gridLayout);
        addUIControls(jPanel, jFrame);
        jFrame.add(jPanel);

        jFrame.setVisible(true);
    }


    private void addUIControls(JPanel jPanel, JFrame jFrame) {


        JLabel flightNameLabel = new JLabel("Flight Name : ");
        jPanel.add(flightNameLabel);

        JTextField flightNameField = new JTextField();
        jPanel.add(flightNameField);

        JLabel fromLocationLabel = new JLabel("From Location : ");
        jPanel.add(fromLocationLabel);

        JTextField fromLocationField = new JTextField();
        jPanel.add(fromLocationField);


        JLabel toLocationLabel = new JLabel("To Location : ");
        jPanel.add(toLocationLabel);

        JTextField toLocationField = new JTextField();
        jPanel.add(toLocationField);

        JLabel dateLabel = new JLabel("Date : ");
        jPanel.add(dateLabel);

        JTextField dateField = new JTextField("dd/MM/yyyy");
        jPanel.add(dateField);


        JLabel seatsAvailableLabel = new JLabel("Seats Available ");
        jPanel.add(seatsAvailableLabel);

        JTextField seatsAvailableField = new JTextField();
        jPanel.add(seatsAvailableField);

        JButton submitButton = new JButton("AddFlight");
        jPanel.add(submitButton);

        JButton logoutButton = new JButton("Logout");
        jPanel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            Login login = new Login();
            try {
                login.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jFrame.setVisible(false);
        });

        submitButton.addActionListener(event -> {
            if (flightNameField.getText().isEmpty()) {
                showAlert("Form Error!", "Please enter Flight Name", jFrame);
                return;
            }
            if (fromLocationField.getText().isEmpty()) {
                showAlert("Form Error!", "Please enter From Location", jFrame);
                return;
            }
            if (toLocationField.getText().isEmpty()) {
                showAlert("Form Error!", "Please enter To Location", jFrame);
                return;
            }
            if (dateField.getText().isEmpty()) {
                showAlert("Form Error!", "Please enter Date", jFrame);
                return;
            }
            if (seatsAvailableField.getText().isEmpty()) {
                showAlert("Form Error!", "Please enter seats available", jFrame);
                return;
            }

            FlightModel flightModel = new FlightModel();
            flightModel.setId(UniqueIdUtil.getUniqueId());
            flightModel.setFlightName(flightNameField.getText());
            flightModel.setFromLocation(fromLocationField.getText());
            flightModel.setToLocation(toLocationField.getText());
            flightModel.setDate(LocalDate.parse(dateField.getText(), formatter));
            flightModel.setSeatsAvailable(Integer.parseInt(seatsAvailableField.getText()));

            try {
                flightDao.insertFlight(flightModel);
                int option = showAlert("Flight Management System", "Added Flight " + flightNameField.getText(), jFrame);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private int showAlert(String title, String message, JFrame jFrame) {
        return JOptionPane.showConfirmDialog(
                jFrame,
                message,
                title, JOptionPane.DEFAULT_OPTION);
    }
}

    

