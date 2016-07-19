package com.example.joeymejias.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joey on 7/18/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String COL_ID = "_id";
    public static final String COL_FIRST = "first";
    public static final String COL_LAST = "last";
    public static final String COL_DOB = "dob";
    public static final String COL_CITY = "city";
    public static final String COL_SSN = "ssn"; //Foreign key

    public static final String JOB_TABLE_NAME = "job";
    public static final String COL_JOB_SSN = "jobssn";
    public static final String COL_COMPANY = "company";
    public static final String COL_EXPERIENCE = "experience";
    public static final String COL_SALARY = "salary";


    private DatabaseHelper(Context context) {
        super(context, "db", null, 1);
    }

    private static DatabaseHelper INSTANCE;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public String getFullInformation(){
        return "";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_EMPLOYEE);
        db.execSQL(SQL_CREATE_ENTRIES_JOB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_EMPLOYEE);
        db.execSQL(SQL_DELETE_ENTRIES_DEPARTMENT);
        onCreate(db);
    }

    private static final String SQL_CREATE_ENTRIES_EMPLOYEE = "CREATE TABLE " +
            EMPLOYEE_TABLE_NAME + " (" +
            COL_SSN + " TEXT PRIMARY KEY," +
            COL_FIRST + " TEXT," +
            COL_LAST + " TEXT," +
            COL_DOB + " INTEGER," +
            COL_CITY + " TEXT" + ")";

    private static final String SQL_CREATE_ENTRIES_JOB = "CREATE TABLE " +
            JOB_TABLE_NAME + " (" +
            COL_JOB_SSN + " TEXT," +
            COL_COMPANY + " TEXT, " +
            COL_SALARY + " INTEGER," +
            COL_EXPERIENCE + " INTEGER" + "," +
            "FOREIGN KEY("+ COL_JOB_SSN +") " +
            "REFERENCES "+ EMPLOYEE_TABLE_NAME+"("+ COL_SSN +") )";

    private static final String SQL_DELETE_ENTRIES_EMPLOYEE =
            "DROP TABLE IF EXISTS " + EMPLOYEE_TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_DEPARTMENT =
            "DROP TABLE IF EXISTS " + JOB_TABLE_NAME;

    public void insertEmployeeRow(Employee employee) {
        //Would be more efficient if added to a list earlier and the list was looped through.(I think)
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SSN, employee.getSsn());
        values.put(COL_FIRST, employee.getFirstName());
        values.put(COL_LAST, employee.getLastName());
        values.put(COL_DOB, employee.getBirthYear());
        values.put(COL_CITY, employee.getCity());
        db.insertOrThrow(EMPLOYEE_TABLE_NAME, null, values);
        db.close();
    }

    public void insertJobRow(Job job) {
        //Would be more efficient if added to a list earlier and the list was looped through. (I think)
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOB_SSN, job.getSsn());
        values.put(COL_COMPANY, job.getCompany());
        values.put(COL_SALARY, job.getSalary());
        values.put(COL_EXPERIENCE, job.getExperience());
        db.insertOrThrow(JOB_TABLE_NAME, null, values);
        db.close();
    }

    public String getHighSalaryInfo(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT company FROM job WHERE salary = (select max(salary) from job)", null);
        String result = " ";
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                result += cursor.getString(cursor.getColumnIndex(COL_COMPANY));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }
    public List<String> getBostonInformation(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT company " +
                "FROM job " +
                "JOIN employee " +
                "ON employee.ssn = job.jobssn " +
                "WHERE city " +
                "LIKE 'Boston'",
                null);
        List<String> boston = new ArrayList<String>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                boston.add(cursor.getString(cursor.getColumnIndex(COL_COMPANY)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return boston;
    }

    public List<String> getEmployees(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM employee", null);
        String result = " ";
        List<String> boston = new ArrayList<String>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                result = cursor.getString(cursor.getColumnIndex(COL_FIRST)) +" " + cursor.getString(cursor.getColumnIndex(COL_LAST));
                boston.add(result);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return boston;
    }
    public List<String> getEmployeesInfo(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT first, last " +
                "FROM employee " +
                "JOIN job " +
                "ON employee.ssn = " +
                "job.jobssn " +
                "WHERE company " +
                "LIKE 'M%'", null);
        String fullName;
        List<String> boston = new ArrayList<String>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                fullName = cursor.getString(cursor.getColumnIndex(COL_FIRST)) + " " + cursor.getString(cursor.getColumnIndex(COL_LAST));
                boston.add(fullName);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return boston;
    }
}