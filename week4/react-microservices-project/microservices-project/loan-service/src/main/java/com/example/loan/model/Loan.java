package com.example.loan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Loan type is required")
    private String loanType; // e.g. HOME, AUTO, PERSONAL

    @NotNull(message = "Loan amount is required")
    private Double loanAmount;

    @NotNull(message = "Outstanding amount is required")
    private Double outstandingAmount;

    private Double interestRate;

    public Loan() {
    }

    public Loan(Long loanId, String customerName, String loanType, Double loanAmount,
                Double outstandingAmount, Double interestRate) {
        this.loanId = loanId;
        this.customerName = customerName;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.outstandingAmount = outstandingAmount;
        this.interestRate = interestRate;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
