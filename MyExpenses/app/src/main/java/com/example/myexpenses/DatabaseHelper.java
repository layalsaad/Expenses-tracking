package com.example.myexpenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 1;
    //Table users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    //Table expense
    private static final String TABLE_EXPENSES = "expenses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EXPENSE_NAME = "expensename";
    private static final String COLUMN_EXPENSE_DESCRIPTION = "description";
    private static final String COLUMN_EXPENSE_AMOUNT = "amount";
    //Table categories
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_CATEGORY_NAME = "categoryname";
    //Table budget
    private static final String TABLE_BUDGETS = "budgets";
    private static final String COLUMN_BUDGET_PERCENTAGE = "percentage";
    //Table user categories
    private static final String TABLE_USERCATS = "usercategories";
    //Table insight
    private static final String TABLE_INSIGHTS = "insights";
    private static final String COLUMN_CONTENT = "content";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EXPENSE_NAME + " TEXT,"
                + COLUMN_EXPENSE_DESCRIPTION + " TEXT,"
                + COLUMN_EXPENSE_AMOUNT + " int,"
                + COLUMN_CATEGORY_NAME + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_CATEGORY_NAME + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_NAME + "),"
                + "FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERNAME + ")"
                + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);


        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + COLUMN_CATEGORY_NAME + " TEXT PRIMARY KEY"
                + ")";
        db.execSQL(CREATE_CATEGORIES_TABLE);


        String CREATE_BUDGETS_TABLE = "CREATE TABLE " + TABLE_BUDGETS + "("
                + COLUMN_CATEGORY_NAME + " TEXT PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_BUDGET_PERCENTAGE + "INTEGER,"
                + "FOREIGN KEY (" + COLUMN_CATEGORY_NAME + ") REFERENCES " + TABLE_USERCATS + "(" + COLUMN_CATEGORY_NAME + "),"
                + "FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERNAME + ")"
                + ")";
        db.execSQL(CREATE_BUDGETS_TABLE);

        String CREATE_USERCATS_TABLE = "CREATE TABLE " + TABLE_USERCATS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_CATEGORY_NAME + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_CATEGORY_NAME + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_NAME + "),"
                + "FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERNAME + ")"
                + ")";
        db.execSQL(CREATE_USERCATS_TABLE);

        String CREATE_INSIGHTS_TABLE = "CREATE TABLE " + TABLE_INSIGHTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CONTENT + " TEXT"
                + ")";
        db.execSQL(CREATE_INSIGHTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERCATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSIGHTS);
        onCreate(db);
    }

    public boolean addUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_PASSWORD, u.getPassword());
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean addDefaultCategories(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ArrayList<String> mycategories = new ArrayList<>();
        mycategories.add("Debt");
        mycategories.add("Products");
        mycategories.add("Marketing");
        mycategories.add("Fees");
        int i;
        for(i=0; i<4; i++) {
            values.put(COLUMN_USERNAME, u.getUsername());
            values.put(COLUMN_CATEGORY_NAME, mycategories.get(i));
            long result = db.insert(TABLE_USERCATS, null, values);
            if (result == -1) return false;
        }
        return true;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean addExpense(Expense expense, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSE_NAME, expense.getName());
        values.put(COLUMN_EXPENSE_DESCRIPTION, expense.getDescription());
        values.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        values.put(COLUMN_CATEGORY_NAME, expense.getCategory());
        values.put(COLUMN_USERNAME, username);
        long result = db.insert(TABLE_EXPENSES, null, values);
        return result != -1;
    }

    public List<Expense> getExpenses(String username) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXPENSES, null, COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense(
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4)
                );
                expenses.add(expense);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return expenses;
    }

    public List<String> getCategories(String username) {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERCATS, null, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public boolean addCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryName);
        long result = db.insert(TABLE_CATEGORIES, null, values);
        return result != -1;
    }

    public boolean addUserCategory(String categoryName, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryName);
        values.put(COLUMN_USERNAME, username);
        long result = db.insert(TABLE_USERCATS, null, values);
        return result != -1;
    }

    public boolean removecategory(String categoryName, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USERCATS,COLUMN_CATEGORY_NAME + "=?" + " AND " + COLUMN_USERNAME +"=?",new String[]{categoryName,username})>0;
    }

    public boolean setBudget(String category, int percentage, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues cv = new ContentValues();
        values.put(COLUMN_BUDGET_PERCENTAGE, percentage);
        cv.put(COLUMN_BUDGET_PERCENTAGE, percentage);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_CATEGORY_NAME, category);

        if(db.insert(TABLE_BUDGETS,null,values) != -1){
            return true;
        } else{
            long result = db.update(TABLE_BUDGETS, cv, COLUMN_CATEGORY_NAME + "=?"+ " AND " + COLUMN_USERNAME +"=?", new String[]{category,username});
            return result != -1;
        }
    }


    public List<String> addInsights(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> insights = new ArrayList<>();
        insights.add("Focus on paying off high-interest debts first to reduce overall interest costs.");
        insights.add("Consolidate multiple debts into a single loan with a lower interest rate to simplify repayments and save on interest.");
        insights.add("Define clear marketing goals (e.g., increase brand awareness, generate leads) to ensure targeted and effective spending.");
        insights.add("Regularly analyze the ROI of marketing campaigns to identify what works and adjust strategies accordingly.");
        insights.add("Utilize social media, content marketing, and email marketing, which can be more cost-effective than traditional advertising.");
        insights.add("Use inventory management techniques for products to avoid overstocking and understocking, which can tie up cash flow.");
        insights.add("Invest in quality control to reduce returns and increase customer satisfaction.");
        insights.add("Negotiate with service providers to reduce fees or switch to more cost-effective alternatives.");
        insights.add("Pay bills on time to avoid late fees, and set reminders or automate payments where possible.");
        insights.add("Develop and enforce expense policies to ensure all spending is necessary and justified.");
        for(String s:insights){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CONTENT,s);
            db.insert(TABLE_INSIGHTS,null,cv);
        }
        return insights;
    }
}
