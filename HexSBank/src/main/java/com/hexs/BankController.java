// File: BankController.java
package com.hexs;

import java.util.*;

/**
 * Controller class for managing user accounts, authentication, balances, and transaction history
 * in the HexS banking system (in-memory version).
 */
public class BankController {
    // Stores the currently logged-in user's username
    private String loggedInUser = null;

    // In-memory user database: maps username -> password
    private final Map<String, String> users = new HashMap<>();

    // In-memory balances: maps username -> account balance
    private final Map<String, Double> balances = new HashMap<>();

    // In-memory transaction history: maps username -> list of transaction descriptions
    private final Map<String, List<String>> transactions = new HashMap<>();

    /**
     * Authenticates a user using their username and password.
     *
     * @param username The username to log in
     * @param password The password for the user
     * @return true if login is successful, false otherwise
     */
    public boolean login(String username, String password) {
        if (users.containsKey(username)) {
            if (users.get(username).equals(password)) {
                loggedInUser = username; // Set session to the logged-in user
                return true;
            }
        }
        return false; // Login failed
    }

    /**
     * Registers a new user if the username is not already taken.
     * Initializes balance and transaction history for the user.
     *
     * @param username The desired username
     * @param password The desired password
     * @return true if registration was successful, false if username already exists
     */
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Username already taken
        }
        // Save user credentials and initialize data
        users.put(username, password);
        balances.put(username, 0.0);
        transactions.put(username, new ArrayList<>());
        return true;
    }

    /**
     * Manually sets the current logged-in user (can be useful for testing or state control).
     *
     * @param username The username to set as currently logged in
     */
    public void setLoggedInUser(String username) {
        this.loggedInUser = username;
    }

    /**
     * Retrieves the username of the currently logged-in user.
     *
     * @return The username of the active session
     */
    public String getLoggedInUsername() {
        return loggedInUser;
    }

    /**
     * Gets the balance of the currently logged-in user.
     *
     * @return Current balance (0.0 if user not found)
     */
    public double getBalance() {
        return balances.getOrDefault(loggedInUser, 0.0);
    }

    /**
     * Deposits a given amount into the currently logged-in user's account.
     *
     * @param amount The amount to deposit
     */
    public void deposit(double amount) {
        balances.put(loggedInUser, getBalance() + amount);
    }

    /**
     * Attempts to withdraw a given amount from the current user's account.
     *
     * @param amount The amount to withdraw
     * @return true if withdrawal is successful; false if insufficient funds
     */
    public boolean withdraw(double amount) {
        double current = getBalance();
        if (amount > current) return false; // Not enough funds
        balances.put(loggedInUser, current - amount);
        return true;
    }

    /**
     * Saves a transaction to the logged-in user's history, with timestamp.
     *
     * @param type   The transaction type (e.g., "Deposit" or "Withdrawal")
     * @param amount The amount involved in the transaction
     */
    public void saveTransaction(String type, double amount) {
        if (!transactions.containsKey(loggedInUser)) return;
        // Add a new transaction string with date
        transactions.get(loggedInUser).add(type + " R" + amount + " on " + new Date());
    }

    /**
     * Retrieves the list of all transactions for the logged-in user.
     *
     * @return List of transaction strings (empty if none exist)
     */
    public List<String> getTransactions() {
        return transactions.getOrDefault(loggedInUser, new ArrayList<>());
    }
}

// T.W.M
