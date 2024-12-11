package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO accDAO;

    public AccountService(){
        accDAO = new AccountDAO();
    }

    /*
     * This method will create a user account given it meets its criteria.
     */
    public Account createAccount(Account acc){
        
        if(acc.getUsername().length() < 4 )
            return null;
        
        if(acc.getPassword().length() < 4)
            return null;

        if(accDAO.isAccount(acc.getAccount_id()) == true)
            return null;
        

        Account result = accDAO.registerAccount(acc);
        return result;
    }

    public Account loginAccount(Account acc){
        return accDAO.accountLogIn(acc);
    }
}
