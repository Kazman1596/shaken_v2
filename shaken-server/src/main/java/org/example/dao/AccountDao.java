package org.example.dao;

import org.example.model.RegisterUserDto;
import org.example.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> getAccounts();

    List<Account> searchAccounts(String input);

    Account getAccountById(int id);

    Account getAccountByUsername(String username);

    Account createAccount(RegisterUserDto user);

    Account updateAccount(Account account);

    boolean deleteAccount(int id);
}

//