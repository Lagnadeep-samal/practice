package com.example.account.service;

import com.example.account.model.Account;
import com.example.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found with id: " + accountId));
    }

    public Account updateAccount(Long accountId, Account updatedAccount) {
        Account existing = getAccountById(accountId);
        existing.setCustomerName(updatedAccount.getCustomerName());
        existing.setAccountType(updatedAccount.getAccountType());
        existing.setBalance(updatedAccount.getBalance());
        existing.setBranchAddress(updatedAccount.getBranchAddress());
        return accountRepository.save(existing);
    }

    public void deleteAccount(Long accountId) {
        Account existing = getAccountById(accountId);
        accountRepository.delete(existing);
    }
}
