// File: SplashScreenFrame.java
package com.hexs;

import javax.swing.*;
import java.awt.*;

/**
 * Splash screen window that appears on application start,
 * shows branding and loading message, then transitions to login.
 */
public class SplashScreenFrame extends JFrame {

    /**
     * Constructs the splash screen frame.
     *
     * @param controller The BankController instance to pass on after splash
     */
    public SplashScreenFrame(BankController controller) {
        setTitle("Welcome to HexS Bank");

        // Calculate responsive size but keep within max bounds
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.min(700, screenSize.width - 100);
        int height = Math.min(400, screenSize.height - 100);
        setSize(width, height);
        setLocationRelativeTo(null);  // Center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);         // Remove window decorations for clean look

        Color darkBlue = new Color(0, 0, 139);  // Dark blue theme color

        // Main container panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Top panel holds the logo aligned left
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        // Load and scale logo image
        ImageIcon rawIcon = new ImageIcon("src/main/resources/HexS.png");
        Image scaledImage = rawIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        topPanel.add(logoLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel shows welcome text and loading message
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome to HexS Bank", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        welcomeLabel.setForeground(darkBlue);
        textPanel.add(welcomeLabel, BorderLayout.CENTER);

        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        loadingLabel.setForeground(darkBlue);
        textPanel.add(loadingLabel, BorderLayout.SOUTH);

        mainPanel.add(textPanel, BorderLayout.CENTER);

        // Timer to auto-close splash and open login after 2.5 seconds
        Timer timer = new Timer(3500, e -> {
            dispose();  // Close splash screen
            // Show login frame on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> new LoginFrame(controller).setVisible(true));
        });
        timer.setRepeats(false);
        timer.start();
    }
}

// T.W.M
