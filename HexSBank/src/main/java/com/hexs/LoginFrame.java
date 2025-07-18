// File: LoginFrame.java
package com.hexs;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the login window for the HexS Bank system.
 * Allows users to log in or create a new account via a GUI form.
 */
public class LoginFrame extends JFrame {

    // Input fields for username and password
    private JTextField userField;
    private JPasswordField passField;

    // Controller to handle logic such as login, registration, etc.
    private BankController controller;

    /**
     * Constructor to build and display the login interface.
     *
     * @param controller The shared BankController instance
     */
    public LoginFrame(BankController controller) {
        this.controller = controller;

        // Window setup
        setTitle("HexS Bank - Login");
        setSize(450, 320);
        setMinimumSize(new Dimension(350, 280));
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // ✅ Background image setup
        ImageIcon backgroundIcon = new ImageIcon("src/main/resources/HexS.jpg");
        Image bgImage = backgroundIcon.getImage();

        // Custom JPanel to paint background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // ✅ Transparent panel to hold form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // Let background show through

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and field
        gbc.gridy = 0;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        userField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(userField, gbc);

        // Password label and field
        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Password:"), gbc);

        passField = new JPasswordField(20);
        gbc.gridx = 1;
        formPanel.add(passField, gbc);

        // ✅ Login and Create Account buttons
        JButton loginBtn = createRoundedButton("Login");
        JButton createBtn = createRoundedButton("Create Account");

        gbc.gridy = 2;
        gbc.gridx = 0;
        formPanel.add(loginBtn, gbc);
        gbc.gridx = 1;
        formPanel.add(createBtn, gbc);

        // Add form panel to background
        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);

        // ✅ Add button actions
        loginBtn.addActionListener(e -> doLogin());
        createBtn.addActionListener(e -> showRegistrationDialog());
    }

    /**
     * Creates a styled, rounded button with consistent color theme.
     *
     * @param text The label for the button
     * @return A styled JButton
     */
    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return btn;
    }

    /**
     * Attempts to log the user in using the provided username and password.
     */
    private void doLogin() {
        String u = userField.getText().trim();
        String p = new String(passField.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        if (controller.login(u, p)) {
            dispose(); // Close login window
            DashboardFrame.open(controller); // Open dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    /**
     * Displays a dialog that lets the user create a new account.
     * If successful, fills in login fields automatically.
     */
    private void showRegistrationDialog() {
        JTextField newUser = new JTextField();
        JPasswordField newPass = new JPasswordField();

        // Build registration form panel
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("New Username:"));
        panel.add(newUser);
        panel.add(new JLabel("New Password:"));
        panel.add(newPass);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Create New Account",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        // Handle result
        if (result == JOptionPane.OK_OPTION) {
            String username = newUser.getText().trim();
            String password = new String(newPass.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both fields are required.");
                return;
            }

            // Try to register new account
            if (controller.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Account created. Please log in.");
                userField.setText(username);
                passField.setText(password);
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

// T.W.M
