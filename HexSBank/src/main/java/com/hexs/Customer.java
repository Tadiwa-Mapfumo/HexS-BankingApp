// File: Customer.java
package com.hexs;

/**
 * Represents a bank customer with a username and password.
 * This class can be extended for future enhancements like contact info or customer ID.
 */
public class Customer {
    // Username used for login and identification
    private String username;

    // Password used for authentication
    private String password;

    /**
     * Constructs a new Customer with a username and password.
     *
     * @param username The customer's username
     * @param password The customer's password
     */
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the customer.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the customer.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}

// T.W.M
