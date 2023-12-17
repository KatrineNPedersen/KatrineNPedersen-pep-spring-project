package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //1 Register User
    public void register(Account newAccount){
        accountRepository.save(newAccount);
    }


    //2 User Login
    public boolean login(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password).isPresent();
    }
    
    //3 Check if username is valid
    public boolean validUser(String username){
        return accountRepository.findByUsername(username).isPresent();
    }

    //4 Check if account id is valid
    public boolean validUserID(Integer id){
        return accountRepository.findById(id).isPresent();
    }

    //5 Get Account by ID
    public Account getAccountByID(Integer id){
        return accountRepository.getById(id);
    }

    // Get Account By Username
    public Account getAccountByUsername(String username){
        return accountRepository.findByUsername(username).get();
    }


}
