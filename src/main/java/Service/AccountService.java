package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty() || account.getPassword().length() < 4) {
            return null;
        }
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account login(Account account) {
        Account acc = accountDAO.getAccountByUsername(account.getUsername());
        if (acc != null && acc.getPassword().equals(account.getPassword())) {
            return acc;
        }
        return null;
    }

    public Account getAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }
}
