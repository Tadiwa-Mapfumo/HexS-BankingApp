// File: DashboardFrame.java
package com.hexs;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represents the main dashboard window for the HexS Bank system.
 * It shows the current user's balance, allows deposit/withdraw actions,
 * and provides access to transaction history and profile info.
 */
public class DashboardFrame extends JFrame {
    // Singleton instance to prevent multiple open dashboards
    private static DashboardFrame instance;

    // Label to display current balance
    private JLabel balanceLabel;

    // Reference to the bank controller for business logic
    private BankController controller;

    /**
     * Opens the dashboard window. If one is already open, it closes it and opens a new one.
     *
     * @param controller The shared BankController instance
     */
    public static void open(BankController controller) {
        if (instance != null) {
            instance.dispose(); // Close existing dashboard if open
        }
        instance = new DashboardFrame(controller);
        instance.setVisible(true);
    }

    /**
     * Constructor that builds the dashboard interface.
     *
     * @param controller The controller managing user data and logic
     */
    private DashboardFrame(BankController controller) {
        this.controller = controller;

        // Frame settings
        setTitle("HexS Bank - Dashboard");
        setSize(700, 500);
        setMinimumSize(new Dimension(500, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setResizable(true);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ✅ Top panel with logo and welcome message
        ImageIcon icon = new ImageIcon("src/main/resources/HexS.png");
        Image scaled = icon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaled));

        JLabel welcome = new JLabel("Welcome, " + controller.getLoggedInUsername() + "!", SwingConstants.CENTER);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 22));
        welcome.setForeground(new Color(0, 102, 204));
        welcome.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(imageLabel, BorderLayout.WEST);
        headerPanel.add(welcome, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // ✅ Center panel with balance and action buttons
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Show current balance
        balanceLabel = new JLabel("", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        gbc.gridy = 0;
        center.add(balanceLabel, gbc);
        updateBalance();

        // Buttons for banking actions
        String[] btns = {"Deposit", "Withdraw", "View Transactions", "View Profile", "Logout"};
        for (int i = 0; i < btns.length; i++) {
            String label = btns[i];
            JButton b = createRoundedButton(label);
            b.addActionListener(e -> handleAction(label));
            gbc.gridy = i + 1;
            center.add(b, gbc);
        }

        add(center, BorderLayout.CENTER);
    }

    /**
     * Creates a styled, rounded button with consistent design.
     *
     * @param text The button label
     * @return A styled JButton
     */
    private JButton createRoundedButton(String text) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(200, 40));
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBackground(new Color(0, 102, 204));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return b;
    }

    /**
     * Handles user interactions when a button is clicked.
     *
     * @param cmd The button label indicating the action to perform
     */
    private void handleAction(String cmd) {
        switch (cmd) {
            case "Deposit" -> doTransaction("Enter amount to deposit:", true);
            case "Withdraw" -> doTransaction("Enter amount to withdraw:", false);
            case "View Transactions" -> showTransactions();
            case "View Profile" -> showProfile();
            case "Logout" -> {
                dispose(); // Close dashboard
                instance = null;
                // Return to login screen
                SwingUtilities.invokeLater(() -> new LoginFrame(controller).setVisible(true));
            }
        }
    }

    /**
     * Prompts the user for a deposit or withdrawal amount and processes it.
     *
     * @param prompt     The message shown to the user
     * @param isDeposit  Whether the transaction is a deposit or withdrawal
     */
    private void doTransaction(String prompt, boolean isDeposit) {
        String input = JOptionPane.showInputDialog(this, prompt);
        if (input == null || input.trim().isEmpty()) return;

        try {
            double amount = Double.parseDouble(input.trim());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a value greater than 0.");
                return;
            }

            if (isDeposit) {
                controller.deposit(amount);
                controller.saveTransaction("Deposit", amount);
                JOptionPane.showMessageDialog(this, "Deposited R" + amount);
            } else {
                boolean success = controller.withdraw(amount);
                if (success) {
                    controller.saveTransaction("Withdraw", amount);
                    JOptionPane.showMessageDialog(this, "Withdrew R" + amount);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds.");
                    return;
                }
            }

            updateBalance();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.");
        }
    }

    /**
     * Updates the displayed balance with the latest value from the controller.
     */
    private void updateBalance() {
        double balance = controller.getBalance();
        balanceLabel.setText("Balance: R" + balance);
    }

    /**
     * Displays the user's transaction history in a scrollable dialog.
     */
    private void showTransactions() {
        List<String> list = controller.getTransactions();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions found.");
        } else {
            JTextArea textArea = new JTextArea(String.join("\n", list));
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Displays a simple profile dialog with the user's username and current balance.
     */
    private void showProfile() {
        JOptionPane.showMessageDialog(this,
                "User: " + controller.getLoggedInUsername() +
                        "\nBalance: R" + controller.getBalance(),
                "Profile", JOptionPane.INFORMATION_MESSAGE);
    }
}

// T.W.M
