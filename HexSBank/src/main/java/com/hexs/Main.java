// File: Main.java
package com.hexs;

import javax.swing.SwingUtilities;

/**
 * Main class serves as the entry point for the HexS Bank application.
 * It initializes the BankController and launches the splash screen on the Event Dispatch Thread.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize the controller that manages business logic and data
        BankController controller = new BankController();

        // Launch GUI on the Event Dispatch Thread (recommended for thread safety)
        SwingUtilities.invokeLater(() -> {
            // Show splash screen which leads to login/dashboard
            new SplashScreenFrame(controller).setVisible(true);
        });
    }
}

// T.W.M
