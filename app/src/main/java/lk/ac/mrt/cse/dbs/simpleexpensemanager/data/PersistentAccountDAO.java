package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentAccountDAO implements AccountDAO{

    DatabaseHelper dbh;
    SQLiteDatabase db;

    public PersistentAccountDAO(DatabaseHelper databaseHelper) {
       this.dbh = databaseHelper;
       db = dbh.getWritableDatabase();
    }


    @Override
    public List<String> getAccountNumbersList() {
        List<String> acc_no_list = new ArrayList<>();
        String query = "SELECT Account_no FROM Account";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                String acc_no = cursor.getString(0);
                acc_no_list.add(acc_no);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return acc_no_list;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> acc_list = new ArrayList<>();
        String query = "SELECT * FROM Account" ;
        Cursor cursor = db.rawQuery(query, null);
        Account acc;
        if (cursor.moveToFirst()){
            do{
                String acc_no = cursor.getString(0);
                String bank = cursor.getString(1);
                String holder = cursor.getString(2);
                double balance = cursor.getDouble(3);
                acc = new Account(acc_no, bank, holder, balance);
                acc_list.add(acc);
            }while (cursor.moveToNext());
        }
        else{
            System.out.println("No accounts yet.");
        }
        cursor.close();
        return acc_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        String query = " SELECT * FROM Account WHERE Account_no = '"+accountNo+"' ";
        Cursor cursor = db.rawQuery(query, null);
        Account acc;
        String acc_no = cursor.getString(0);
        String bank = cursor.getString(1);
        String holder = cursor.getString(2);
        double balance = cursor.getDouble(3);
        acc = new Account(acc_no, bank, holder, balance);
        cursor.close();
        return acc;
    }

    @Override
    public void addAccount(Account account) {
        String query = "INSERT INTO Account VALUES (' " + account.getAccountNo() + " ' , ' " + account.getBankName() + " ' , ' " + account.getAccountHolderName() + " ' , "  + account.getBalance() + " )";
        db.execSQL(query);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String query = "DELETE FROM Account WHERE acc_no = '"+accountNo+"' ";
        db.execSQL(query);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        String str;
        if (expenseType == ExpenseType.INCOME)
            str = "+" ;
        else
            str = "-";
        String query = " UPDATE Account SET Balance = Balance" + str + amount + " WHERE Account_no = '"+accountNo+"'";
        db.execSQL(query);
    }
}
