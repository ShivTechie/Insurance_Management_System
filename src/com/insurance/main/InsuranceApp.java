package com.insurance.main;

import com.insurance.dao.PolicyDAO;
import com.insurance.dao.CustomerDAO;
import com.insurance.dao.ClaimDAO;
import com.insurance.exception.*;
import com.insurance.model.Policy;
import com.insurance.model.Customer;
import com.insurance.model.Claim;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class InsuranceApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PolicyDAO policyDAO = new PolicyDAO();
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final ClaimDAO claimDAO = new ClaimDAO();

    public static void main(String[] args) {
        System.out.println("===== Welcome to the Insurance Management System =====");
        while (true) {
            try {
                System.out.println("\nMain Menu:");
                System.out.println("1. Policy Management");
                System.out.println("2. Customer Management");
                System.out.println("3. Claim Management");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        policyManagementMenu();
                        break;
                    case 2:
                        customerManagementMenu();
                        break;
                    case 3:
                        claimManagementMenu();
                        break;
                    case 4:
                        System.out.println("Thank you for using the Insurance Management System. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            } catch (DatabaseConnectionException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // ============================ Policy Management ============================

    private static void policyManagementMenu() throws DatabaseConnectionException {
        while (true) {
            try {
                System.out.println("\nPolicy Management Menu:");
                System.out.println("1. Add Policy");
                System.out.println("2. View Policy by ID");
                System.out.println("3. View All Policies");
                System.out.println("4. Update Policy");
                System.out.println("5. Delete Policy");
                System.out.println("6. Back to Main Menu");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addPolicy();
                        break;
                    case 2:
                        viewPolicyById();
                        break;
                    case 3:
                        viewAllPolicies();
                        break;
                    case 4:
                        updatePolicy();
                        break;
                    case 5:
                        deletePolicy();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            } catch (PolicyNotFoundException e) {
                System.out.println("Policy not found: " + e.getMessage());
            }
        }
    }

    private static void addPolicy() throws DatabaseConnectionException {
        System.out.println("\n--- Add New Policy ---");
        System.out.print("Enter Policy Number: ");
        String policyNumber = scanner.nextLine();

        System.out.print("Enter Policy Type: ");
        String type = scanner.nextLine();

        System.out.print("Enter Coverage Amount: ");
        double coverageAmount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Premium Amount: ");
        double premiumAmount = Double.parseDouble(scanner.nextLine());

        Policy policy = new Policy();
        policy.setPolicyNumber(policyNumber);
        policy.setType(type);
        policy.setCoverageAmount(coverageAmount);
        policy.setPremiumAmount(premiumAmount);

        policyDAO.addPolicy(policy);
        System.out.println("Policy added successfully with ID: " + policy.getPolicyId());
    }

    private static void viewPolicyById() throws DatabaseConnectionException, PolicyNotFoundException {
        System.out.println("\n--- View Policy by ID ---");
        System.out.print("Enter Policy ID: ");
        int policyId = Integer.parseInt(scanner.nextLine());

        Policy policy = policyDAO.getPolicyById(policyId);
        if (policy != null) {
            System.out.println(policy);
        } else {
            throw new PolicyNotFoundException("Policy with ID " + policyId + " does not exist.");
        }
    }

    private static void viewAllPolicies() throws DatabaseConnectionException {
        System.out.println("\n--- View All Policies ---");
        List<Policy> policies = policyDAO.getAllPolicies();
        if (policies.isEmpty()) {
            System.out.println("No policies found.");
        } else {
            policies.forEach(System.out::println);
        }
    }

    private static void updatePolicy() throws DatabaseConnectionException, PolicyNotFoundException {
        System.out.println("\n--- Update Policy ---");
        System.out.print("Enter Policy ID to update: ");
        int policyId = Integer.parseInt(scanner.nextLine());

        Policy existingPolicy = policyDAO.getPolicyById(policyId);
        if (existingPolicy == null) {
            throw new PolicyNotFoundException("Policy with ID " + policyId + " does not exist.");
        }

        System.out.print("Enter new Policy Number (" + existingPolicy.getPolicyNumber() + "): ");
        String policyNumber = scanner.nextLine();
        policyNumber = policyNumber.isEmpty() ? existingPolicy.getPolicyNumber() : policyNumber;

        System.out.print("Enter new Policy Type (" + existingPolicy.getType() + "): ");
        String type = scanner.nextLine();
        type = type.isEmpty() ? existingPolicy.getType() : type;

        System.out.print("Enter new Coverage Amount (" + existingPolicy.getCoverageAmount() + "): ");
        String coverageAmountInput = scanner.nextLine();
        double coverageAmount = coverageAmountInput.isEmpty() ? existingPolicy.getCoverageAmount() : Double.parseDouble(coverageAmountInput);

        System.out.print("Enter new Premium Amount (" + existingPolicy.getPremiumAmount() + "): ");
        String premiumAmountInput = scanner.nextLine();
        double premiumAmount = premiumAmountInput.isEmpty() ? existingPolicy.getPremiumAmount() : Double.parseDouble(premiumAmountInput);

        existingPolicy.setPolicyNumber(policyNumber);
        existingPolicy.setType(type);
        existingPolicy.setCoverageAmount(coverageAmount);
        existingPolicy.setPremiumAmount(premiumAmount);

        policyDAO.updatePolicy(existingPolicy);
        System.out.println("Policy updated successfully.");
    }

    private static void deletePolicy() throws DatabaseConnectionException, PolicyNotFoundException {
        System.out.println("\n--- Delete Policy ---");
        System.out.print("Enter Policy ID to delete: ");
        int policyId = Integer.parseInt(scanner.nextLine());

        Policy existingPolicy = policyDAO.getPolicyById(policyId);
        if (existingPolicy == null) {
            throw new PolicyNotFoundException("Policy with ID " + policyId + " does not exist.");
        }

        policyDAO.deletePolicy(policyId);
        System.out.println("Policy deleted successfully.");
    }

    // ============================ Customer Management ============================

    private static void customerManagementMenu() throws DatabaseConnectionException {
        while (true) {
            try {
                System.out.println("\nCustomer Management Menu:");
                System.out.println("1. Add Customer");
                System.out.println("2. View Customer by ID");
                System.out.println("3. View All Customers");
                System.out.println("4. Update Customer");
                System.out.println("5. Delete Customer");
                System.out.println("6. Back to Main Menu");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        viewCustomerById();
                        break;
                    case 3:
                        viewAllCustomers();
                        break;
                    case 4:
                        updateCustomer();
                        break;
                    case 5:
                        deleteCustomer();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            } catch (CustomerNotFoundException e) {
                System.out.println("Customer not found: " + e.getMessage());
            }
        }
    }

    private static void addCustomer() throws DatabaseConnectionException {
        System.out.println("\n--- Add New Customer ---");
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Customer Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter Customer Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);

        customerDAO.addCustomer(customer);
        System.out.println("Customer added successfully with ID: " + customer.getCustomerId());
    }

    private static void viewCustomerById() throws DatabaseConnectionException, CustomerNotFoundException {
        System.out.println("\n--- View Customer by ID ---");
        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer != null) {
            System.out.println(customer);
        } else {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }
    }

    private static void viewAllCustomers() throws DatabaseConnectionException {
        System.out.println("\n--- View All Customers ---");
        List<Customer> customers = customerDAO.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void updateCustomer() throws DatabaseConnectionException, CustomerNotFoundException {
        System.out.println("\n--- Update Customer ---");
        System.out.print("Enter Customer ID to update: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        Customer existingCustomer = customerDAO.getCustomerById(customerId);
        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        System.out.print("Enter new Customer Name (" + existingCustomer.getName() + "): ");
        String name = scanner.nextLine();
        name = name.isEmpty() ? existingCustomer.getName() : name;

        System.out.print("Enter new Customer Email (" + existingCustomer.getEmail() + "): ");
        String email = scanner.nextLine();
        email = email.isEmpty() ? existingCustomer.getEmail() : email;

        System.out.print("Enter new Customer Phone Number (" + existingCustomer.getPhoneNumber() + "): ");
        String phoneNumber = scanner.nextLine();
        phoneNumber = phoneNumber.isEmpty() ? existingCustomer.getPhoneNumber() : phoneNumber;

        System.out.print("Enter new Customer Address (" + existingCustomer.getAddress() + "): ");
        String address = scanner.nextLine();
        address = address.isEmpty() ? existingCustomer.getAddress() : address;

        existingCustomer.setName(name);
        existingCustomer.setEmail(email);
        existingCustomer.setPhoneNumber(phoneNumber);
        existingCustomer.setAddress(address);

        customerDAO.updateCustomer(existingCustomer);
        System.out.println("Customer updated successfully.");
    }

    private static void deleteCustomer() throws DatabaseConnectionException, CustomerNotFoundException {
        System.out.println("\n--- Delete Customer ---");
        System.out.print("Enter Customer ID to delete: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        Customer existingCustomer = customerDAO.getCustomerById(customerId);
        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        customerDAO.deleteCustomer(customerId);
        System.out.println("Customer deleted successfully.");
    }

    // ============================ Claim Management ============================

    private static void claimManagementMenu() throws DatabaseConnectionException {
        while (true) {
            try {
                System.out.println("\nClaim Management Menu:");
                System.out.println("1. Submit Claim");
                System.out.println("2. View Claim by ID");
                System.out.println("3. View All Claims");
                System.out.println("4. Update Claim");
                System.out.println("5. Delete Claim");
                System.out.println("6. Back to Main Menu");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        submitClaim();
                        break;
                    case 2:
                        viewClaimById();
                        break;
                    case 3:
                        viewAllClaims();
                        break;
                    case 4:
                        updateClaim();
                        break;
                    case 5:
                        deleteClaim();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            } catch (ClaimNotFoundException e) {
                System.out.println("Claim not found: " + e.getMessage());
            }
        }
    }

    private static void submitClaim() throws DatabaseConnectionException {
        System.out.println("\n--- Submit New Claim ---");
        System.out.print("Enter Policy ID: ");
        int policyId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Claim Date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        Date claimDate = Date.valueOf(LocalDate.parse(dateInput));

        System.out.print("Enter Claim Status (submitted/processed): ");
        String status = scanner.nextLine();

        Claim claim = new Claim();
        claim.setPolicyId(policyId);
        claim.setCustomerId(customerId);
        claim.setClaimDate(claimDate);
        claim.setStatus(status);

        claimDAO.submitClaim(claim);
        System.out.println("Claim submitted successfully with ID: " + claim.getClaimId());
    }

    private static void viewClaimById() throws DatabaseConnectionException, ClaimNotFoundException {
        System.out.println("\n--- View Claim by ID ---");
        System.out.print("Enter Claim ID: ");
        int claimId = Integer.parseInt(scanner.nextLine());

        Claim claim = claimDAO.getClaimById(claimId);
        if (claim != null) {
            System.out.println(claim);
        } else {
            throw new ClaimNotFoundException("Claim with ID " + claimId + " does not exist.");
        }
    }

    private static void viewAllClaims() throws DatabaseConnectionException {
        System.out.println("\n--- View All Claims ---");
        List<Claim> claims = claimDAO.getAllClaims();
        if (claims.isEmpty()) {
            System.out.println("No claims found.");
        } else {
            claims.forEach(System.out::println);
        }
    }

    private static void updateClaim() throws DatabaseConnectionException, ClaimNotFoundException {
        System.out.println("\n--- Update Claim ---");
        System.out.print("Enter Claim ID to update: ");
        int claimId = Integer.parseInt(scanner.nextLine());

        Claim existingClaim = claimDAO.getClaimById(claimId);
        if (existingClaim == null) {
            throw new ClaimNotFoundException("Claim with ID " + claimId + " does not exist.");
        }

        System.out.print("Enter new Policy ID (" + existingClaim.getPolicyId() + "): ");
        String policyIdInput = scanner.nextLine();
        int policyId = policyIdInput.isEmpty() ? existingClaim.getPolicyId() : Integer.parseInt(policyIdInput);

        System.out.print("Enter new Customer ID (" + existingClaim.getCustomerId() + "): ");
        String customerIdInput = scanner.nextLine();
        int customerId = customerIdInput.isEmpty() ? existingClaim.getCustomerId() : Integer.parseInt(customerIdInput);

        System.out.print("Enter new Claim Date (" + existingClaim.getClaimDate() + ") (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        Date claimDate = dateInput.isEmpty() ? existingClaim.getClaimDate() : Date.valueOf(LocalDate.parse(dateInput));

        System.out.print("Enter new Claim Status (" + existingClaim.getStatus() + "): ");
        String status = scanner.nextLine();
        status = status.isEmpty() ? existingClaim.getStatus() : status;

        existingClaim.setPolicyId(policyId);
        existingClaim.setCustomerId(customerId);
        existingClaim.setClaimDate(claimDate);
        existingClaim.setStatus(status);

        claimDAO.updateClaim(existingClaim);
        System.out.println("Claim updated successfully.");
    }

    private static void deleteClaim() throws DatabaseConnectionException, ClaimNotFoundException {
        System.out.println("\n--- Delete Claim ---");
        System.out.print("Enter Claim ID to delete: ");
        int claimId = Integer.parseInt(scanner.nextLine());

        Claim existingClaim = claimDAO.getClaimById(claimId);
        if (existingClaim == null) {
            throw new ClaimNotFoundException("Claim with ID " + claimId + " does not exist.");
        }

        claimDAO.deleteClaim(claimId);
        System.out.println("Claim deleted successfully.");
    }
}
