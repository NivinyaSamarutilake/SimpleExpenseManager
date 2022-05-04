package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentExpenseManager extends ExpenseManager{

    MainActivity mainActivity;
    public PersistentExpenseManager(MainActivity mainActivity) throws ExpenseManagerException {
        this.mainActivity = mainActivity;
        setup();
    }
    @Override
    public void setup() throws ExpenseManagerException {
        DatabaseHelper expDb;
        expDb = new DatabaseHelper(mainActivity);

        PersistentTransactionDAO trans_dao = new PersistentTransactionDAO(expDb);
        setTransactionsDAO(trans_dao);
        PersistentAccountDAO acc_dao = new PersistentAccountDAO(expDb);
        setAccountsDAO(acc_dao);
    }
}
