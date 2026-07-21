package com.example.loan.service;

import com.example.loan.model.Loan;
import com.example.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("Loan not found with id: " + loanId));
    }

    public Loan updateLoan(Long loanId, Loan updatedLoan) {
        Loan existing = getLoanById(loanId);
        existing.setCustomerName(updatedLoan.getCustomerName());
        existing.setLoanType(updatedLoan.getLoanType());
        existing.setLoanAmount(updatedLoan.getLoanAmount());
        existing.setOutstandingAmount(updatedLoan.getOutstandingAmount());
        existing.setInterestRate(updatedLoan.getInterestRate());
        return loanRepository.save(existing);
    }

    public void deleteLoan(Long loanId) {
        Loan existing = getLoanById(loanId);
        loanRepository.delete(existing);
    }
}
