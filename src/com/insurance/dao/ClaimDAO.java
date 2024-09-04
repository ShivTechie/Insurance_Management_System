package com.insurance.dao;

import com.insurance.exception.DatabaseConnectionException;
import com.insurance.model.Claim;
import com.insurance.util.DatabaseUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    public void submitClaim(Claim claim) throws DatabaseConnectionException {
        String query = "INSERT INTO Claim (policy_id, customer_id, claim_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, claim.getPolicyId());
            stmt.setInt(2, claim.getCustomerId());
            stmt.setDate(3, claim.getClaimDate());
            stmt.setString(4, claim.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error submitting claim", e);
        }
    }

    public Claim getClaimById(int claimId) throws DatabaseConnectionException {
        String query = "SELECT * FROM Claim WHERE claim_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, claimId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Claim claim = new Claim();
                claim.setClaimId(rs.getInt("claim_id"));
                claim.setPolicyId(rs.getInt("policy_id"));
                claim.setCustomerId(rs.getInt("customer_id"));
                claim.setClaimDate(rs.getDate("claim_date"));
                claim.setStatus(rs.getString("status"));
                return claim;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving claim", e);
        }
        return null;
    }

    public List<Claim> getAllClaims() throws DatabaseConnectionException {
        String query = "SELECT * FROM Claim";
        List<Claim> claims = new ArrayList<>();
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Claim claim = new Claim();
                claim.setClaimId(rs.getInt("claim_id"));
                claim.setPolicyId(rs.getInt("policy_id"));
                claim.setCustomerId(rs.getInt("customer_id"));
                claim.setClaimDate(rs.getDate("claim_date"));
                claim.setStatus(rs.getString("status"));
                claims.add(claim);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving claims", e);
        }
        return claims;
    }

    public void updateClaim(Claim claim) throws DatabaseConnectionException {
        String query = "UPDATE Claim SET policy_id = ?, customer_id = ?, claim_date = ?, status = ? WHERE claim_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, claim.getPolicyId());
            stmt.setInt(2, claim.getCustomerId());
            stmt.setDate(3, claim.getClaimDate());
            stmt.setString(4, claim.getStatus());
            stmt.setInt(5, claim.getClaimId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error updating claim", e);
        }
    }

    public void deleteClaim(int claimId) throws DatabaseConnectionException {
        String query = "DELETE FROM Claim WHERE claim_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, claimId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error deleting claim", e);
        }
    }
}
