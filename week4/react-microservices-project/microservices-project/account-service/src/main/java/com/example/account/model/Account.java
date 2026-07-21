package com.example.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Account type is required")
    private String accountType; // e.g. SAVINGS, CURRENT

    @NotNull(message = "Balance is required")
    private Double balance;

    private String branchAddress;

    public Account() {
    }

    public Account(Long accountId, String customerName, String accountType, Double balance, String branchAddress) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.accountType = accountType;
        this.balance = balance;
        this.branchAddress = branchAddress;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }
}
