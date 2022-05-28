/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kitti
 */
public class Login extends JFrame{
    
    UserDao userDao = new UserDao();

    public void start() throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Flight System - Login");
        jFrame.setSize(400, 300);
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(40, 25, 40, 25));
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);
        jPanel.setLayout(gridLayout);
        addUIControls(jPanel, jFrame);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }


    private void addUIControls(JPanel jPanel, JFrame jFrame) throws IOException {

        JLabel usernameLabel = new JLabel("Username : ");
        jPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        jPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password : ");
        jPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        jPanel.add(passwordField);

        JButton submitButton = new JButton("Login");
        jPanel.add(submitButton);

        JButton registerButton = new JButton("Register");
        jPanel.add(registerButton);


        submitButton.addActionListener(event -> {
            if (usernameField.getText().isEmpty()) {
                showAlert("Form Error!", "Please enter your username", jFrame);
                return;
            }
            if (passwordField.getText().isEmpty()) {
                showAlert("Form Error!", "Please enter your password", jFrame);
                return;
            }

            try {
                UserModel user = userDao.getUserByUserNameAndPassword(usernameField.getText(), passwordField.getText());
                if (user == null) {
                    showAlert("Login Failed!", "Invalid username or password", jFrame);
                } else {
                    int option = showAlert("Login Successful!", "Welcome " + usernameField.getText(), jFrame);
                    if(option == 0) {
                        if(user.getType().equals("ADMIN")) {
                            AddFlight addFlight = new AddFlight();
                            addFlight.start();
                            jFrame.setVisible(false);
                        } else {
                            UserSession.setUserId(user.getId());
                            UserHome userHome = new UserHome();
                            userHome.start();
                            jFrame.setVisible(false);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        registerButton.addActionListener(event -> {
            Register register = new Register();
            register.start();
            jFrame.setVisible(false);
        });
    }

    private int showAlert(String title, String message, JFrame jFrame) {
        return JOptionPane.showConfirmDialog(
                jFrame,
                message,
                title, JOptionPane.DEFAULT_OPTION);
    }

}
