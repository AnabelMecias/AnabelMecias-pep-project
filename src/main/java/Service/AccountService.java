package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;
    
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    public List<Account> getAllAcounts () {
        return accountDAO.getAllAcounts();
    }
    public Account getAccountByUserName (String username) {
        Account existsAccount = null;
        existsAccount = accountDAO.getAccountByUserName(username);
        if (existsAccount == null) {
            System.out.println("Account with username " + username + " does not exist.");
        }
        return existsAccount;
    }

    public Account getAccountByID (int account_id){
        Account existsAccount = null;
        existsAccount= accountDAO.getAccountByID(account_id);
        if (existsAccount == null) {
            System.out.println("Account with ID " + account_id + " does not exist.");
        }
        return existsAccount;
    }

    public Account addAccount (Account account) {
        return accountDAO.createAccount(account);
    }

    public Account updateAccount (int account_id, Account account) {
        Account existsAccount = accountDAO.getAccountByID(account_id);
        if (existsAccount != null) {
            existsAccount.setUsername(account.getUsername());
            existsAccount.setPassword(account.getPassword());
            return existsAccount;
        } else {
            System.out.println("Account with ID " + account_id + " does not exist.");
            return null;
        }
    }

    public Account registerUser(Account account) {
        if (account.getUsername().isEmpty() == false && account.getPassword().length() >= 4 && getAccountByUserName(account.getUsername()) == null) {
            return addAccount(account);
        }
        return null;
    }

    public Account login (Account account) {
        Account existsAccount = getAccountByUserName(account.getUsername());
        if(existsAccount != null && existsAccount.getPassword().equals(account.getPassword()) ) {
            return existsAccount;
        }else{
            return null;
        }
    }
}
