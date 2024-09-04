package com.insurance.dao;

import com.insurance.exception.DatabaseConnectionException;
import com.insurance.model.Policy;
import com.insurance.util.DatabaseUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyDAO {

    public void addPolicy(Policy policy) throws DatabaseConnectionException {
        String query = "INSERT INTO Policy (policy_number, type, coverage_amount, premium_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, policy.getPolicyNumber());
            stmt.setString(2, policy.getType());
            stmt.setDouble(3, policy.getCoverageAmount());
            stmt.setDouble(4, policy.getPremiumAmount());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error adding policy", e);
        }
    }

    public Policy getPolicyById(int policyId) throws DatabaseConnectionException {
        String query = "SELECT * FROM Policy WHERE policy_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, policyId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Policy policy = new Policy();
                policy.setPolicyId(rs.getInt("policy_id"));
                policy.setPolicyNumber(rs.getString("policy_number"));
                policy.setType(rs.getString("type"));
                policy.setCoverageAmount(rs.getDouble("coverage_amount"));
                policy.setPremiumAmount(rs.getDouble("premium_amount"));
                return policy;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving policy", e);
        }
        return null;
    }

    public List<Policy> getAllPolicies() throws DatabaseConnectionException {
        String query = "SELECT * FROM Policy";
        List<Policy> policies = new ArrayList<>();
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Policy policy = new Policy();
                policy.setPolicyId(rs.getInt("policy_id"));
                policy.setPolicyNumber(rs.getString("policy_number"));
                policy.setType(rs.getString("type"));
                policy.setCoverageAmount(rs.getDouble("coverage_amount"));
                policy.setPremiumAmount(rs.getDouble("premium_amount"));
                policies.add(policy);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving policies", e);
        }
        return policies;
    }

    public void updatePolicy(Policy policy) throws DatabaseConnectionException {
        String query = "UPDATE Policy SET policy_number = ?, type = ?, coverage_amount = ?, premium_amount = ? WHERE policy_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, policy.getPolicyNumber());
            stmt.setString(2, policy.getType());
            stmt.setDouble(3, policy.getCoverageAmount());
            stmt.setDouble(4, policy.getPremiumAmount());
            stmt.setInt(5, policy.getPolicyId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error updating policy", e);
        }
    }

    public void deletePolicy(int policyId) throws DatabaseConnectionException {
        String query = "DELETE FROM Policy WHERE policy_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, policyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error deleting policy", e);
        }
    }
}
