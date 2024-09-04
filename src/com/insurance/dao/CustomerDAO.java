package com.insurance.dao;

import com.insurance.exception.DatabaseConnectionException;
import com.insurance.model.Customer;
import com.insurance.util.DatabaseUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void addCustomer(Customer customer) throws DatabaseConnectionException {
        String query = "INSERT INTO Customer (name, email, phone_number, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhoneNumber());
            stmt.setString(4, customer.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error adding customer", e);
        }
    }

    public Customer getCustomerById(int customerId) throws DatabaseConnectionException {
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                return customer;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving customer", e);
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws DatabaseConnectionException {
        String query = "SELECT * FROM Customer";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving customers", e);
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws DatabaseConnectionException {
        String query = "UPDATE Customer SET name = ?, email = ?, phone_number = ?, address = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhoneNumber());
            stmt.setString(4, customer.getAddress());
            stmt.setInt(5, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error updating customer", e);
        }
    }

    public void deleteCustomer(int customerId) throws DatabaseConnectionException {
        String query = "DELETE FROM Customer WHERE customer_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error deleting customer", e);
        }
    }
}
