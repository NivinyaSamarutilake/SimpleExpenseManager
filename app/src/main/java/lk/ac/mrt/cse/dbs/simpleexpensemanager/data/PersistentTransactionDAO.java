package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.ExpenseLogsFragment;

public class PersistentTransactionDAO implements TransactionDAO{

    DatabaseHelper dbh;
    SQLiteDatabase db;

    public PersistentTransactionDAO(DatabaseHelper databaseHelper) {
        this.dbh = databaseHelper;
        db = dbh.getWritableDatabase();
    }
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
        String str_date = date_format.format(date);
        String query = "INSERT INTO Log VALUES ( ' " + str_date + " ' , ' " + accountNo + " ' , ' " + expenseType + " ' ," + amount + ")";
        db.execSQL(query);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Log" ;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                String dt = cursor.getString(0);
                String acc_no = cursor.getString(1);
                String exp_type = cursor.getString(2).trim();
                double amt = cursor.getDouble(3);

                try {
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dt);
                    if (exp_type.equalsIgnoreCase("INCOME"))
                        transactions.add(new Transaction(date, acc_no, ExpenseType.INCOME , amt));
                    else
                        transactions.add(new Transaction(date, acc_no, ExpenseType.EXPENSE , amt));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }

        cursor.close();
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = new ArrayList<>();
        transactions = getAllTransactionLogs();
        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }
}
