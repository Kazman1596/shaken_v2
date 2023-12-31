package org.example.controller;

import org.example.dao.AccountDao;
import org.example.exception.DaoException;
import org.example.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {

    @Autowired
    public AccountDao accountDao;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Account> getAccounts(@RequestParam(defaultValue = "")String input) {
        if (!input.equals("")) {
            return accountDao.searchAccounts(input);
        } else {
            return accountDao.getAccounts();
        }
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable int id) {
        Account account = accountDao.getAccountById(id);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account was not found");
        } else {
            return account;
        }
    }

    @RequestMapping(path = "/user/{username}", method = RequestMethod.GET)
    public Account getAccountByUsername(@PathVariable String username) {
        Account account = accountDao.getAccountByUsername(username);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account was not found");
        } else {
            return account;
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Account updateAccount(@PathVariable int id, @Valid @RequestBody Account account) {
        try {
            account.setId(id);
            return accountDao.updateAccount(account);
        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        }
    }

}
