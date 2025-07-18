// File: BankAccount.java
// Package declaration to organize this class under the 'com.hexs' namespace
package com.hexs;

/**
 * Represents a bank account with basic deposit and withdrawal functionality.
 * Each account is associated with a specific customer via their customerId.
 */
public class BankAccount {
    // Unique identifier for the customer who owns this account
    private int customerId;

    // Current balance in the account
    private double balance;

    /**
     * Constructor to initialize a bank account with a customer ID and starting balance.
     *
     * @param customerId ID of the customer who owns the account
     * @param balance Initial balance of the account
     */
    public BankAccount(int customerId, double balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the current account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Deposits a positive amount into the account.
     *
     * @param amount The amount to be deposited
     * @return true if the deposit was successful; false if the amount is invalid
     */
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount; // Add amount to current balance
            return true;
        }
        return false; // Deposit failed due to non-positive amount
    }

    /**
     * Withdraws a specified amount from the account if sufficient funds are available.
     *
     * @param amount The amount to be withdrawn
     * @return true if withdrawal was successful; false if amount is invalid or exceeds balance
     */
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount; // Deduct amount from balance
            return true;
        }
        return false; // Withdrawal failed due to invalid amount or insufficient funds
    }
}

// T.W.M
