package com.example.joeymejias.myapplication;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button add;
    Button employeesWorkingAtTheSameCompany;
    Button companiesInBoston;
    Button companyWithTheHighestSalary;
    ListView listView;
    FloatingActionButton fab;
    DatabaseHelper databaseHelper;
    RelativeLayout relativeLayout;
    TextView text;
    ArrayAdapter<String> arrayAdapter;
    List<String> arrayList;

    Employee employee1,
            employee2,
            employee3,
            employee4,
            employee5,
            employee6,
            employee7,
            employee8;
    Job job1,
            job2,
            job3,
            job4,
            job5,
            job6,
            job7,
            job8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = DatabaseHelper.getInstance(MainActivity.this);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        listView = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<String>();
        arrayList = databaseHelper.getEmployees();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        add = (Button) findViewById(R.id.addButton);
        employeesWorkingAtTheSameCompany = (Button) findViewById(R.id.employeesButton);
        companiesInBoston = (Button) findViewById(R.id.companiesButton);
        companyWithTheHighestSalary = (Button) findViewById(R.id.companiesSalary);
        text = (TextView) findViewById(R.id.text);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        employee1 = new Employee("123-04-5678", "John", "Smith", 1973, "NY");
        employee2 = new Employee("123-04-5679", "David", "McWill", 1982, "Seattle");
        employee3 = new Employee("123-04-5680", "Katerina", "Wise", 1973, "Boston");
        employee4 = new Employee("123-04-5681", "Donald", "Lee", 1992, "London");
        employee5 = new Employee("123-04-5682", "Gary", "Henwood", 1987, "Las Vegas");
        employee6 = new Employee("123-04-5683", "Anthony", "Bright", 1963, "Seattle");
        employee7 = new Employee("123-04-5684", "William", "Newey", 1995, "Boston");
        employee8 = new Employee("123-04-5685", "Melony", "Smith", 1970, "Chicago");

        job1 = new Job("123-04-5678", "Fuzz", 60, 1);
        job2 = new Job("123-04-5679", "GA", 70, 2);
        job3 = new Job("123-04-5680", "Little Place", 120, 5);
        job4 = new Job("123-04-5681", "Macy's", 78, 3);
        job5 = new Job("123-04-5682", "New Life", 65, 1);
        job6 = new Job("123-04-5683", "Believe", 158, 6);
        job7 = new Job("123-04-5684", "Macy's", 200, 8);
        job8 = new Job("123-04-5685", "Stop", 299, 12);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewToDoAlertDialog();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add all the employees and all the jobs
                //Would be more efficient if added to a list earlier and the list was looped through. (I think)
                try{
                    databaseHelper.insertEmployeeRow(employee1);
                    databaseHelper.insertEmployeeRow(employee2);
                    databaseHelper.insertEmployeeRow(employee3);
                    databaseHelper.insertEmployeeRow(employee4);
                    databaseHelper.insertEmployeeRow(employee5);
                    databaseHelper.insertEmployeeRow(employee6);
                    databaseHelper.insertEmployeeRow(employee7);
                    databaseHelper.insertEmployeeRow(employee8);

                    databaseHelper.insertJobRow(job1);
                    databaseHelper.insertJobRow(job2);
                    databaseHelper.insertJobRow(job3);
                    databaseHelper.insertJobRow(job4);
                    databaseHelper.insertJobRow(job5);
                    databaseHelper.insertJobRow(job6);
                    databaseHelper.insertJobRow(job7);
                    databaseHelper.insertJobRow(job8);}
                catch (SQLiteConstraintException ex){
                    //Catch primary key entered twice. Do nothing if primary key already exists.
                }
            }
        });
        companiesInBoston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query Boston info and set listView items
                arrayList = databaseHelper.getBostonInformation();
                for (String s: arrayList) {
                    Log.i("employee", s);
                }
                arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
            }
        });
        employeesWorkingAtTheSameCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query Employees and set listView items
                arrayList = databaseHelper.getEmployeesInfo();
                arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);
                for (String s: arrayList) {
                    Log.i("employee", s);
                }
                listView.setAdapter(arrayAdapter);
            }
        });
        companyWithTheHighestSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query Salaries and set textView
                text.setText("Company with the highest salary:" + databaseHelper.getHighSalaryInfo() + " ");
            }
        });
    }

    public void createNewToDoAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_data, null);
        dialogBuilder.setView(dialogView);

        final EditText ssn = (EditText) dialogView.findViewById(R.id.ssn);
        final EditText firstName = (EditText) dialogView.findViewById(R.id.firstName);
        final EditText lastName = (EditText) dialogView.findViewById(R.id.lastName);
        final EditText dob = (EditText) dialogView.findViewById(R.id.dob_edittext);
        final EditText city = (EditText) dialogView.findViewById(R.id.city);

        dialogBuilder.setPositiveButton("Add Data", null);
        dialogBuilder.setNegativeButton("Cancel", null);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (checkInput(ssn) && checkInput(firstName) && checkInput(lastName) && checkInput(dob) && checkInput(city)){

                    Employee employee = new Employee();
                    employee.setFirstName(firstName.getText().toString());
                    employee.setLastName(lastName.getText().toString());
                    employee.setSsn(ssn.getText().toString());
                    employee.setCity(city.getText().toString());
                    employee.setBirthYear(Integer.valueOf(dob.getText().toString()));
                    databaseHelper.insertEmployeeRow(employee);
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public boolean checkInput(EditText editText) {
        if (editText.getText().toString().length() == 0) {
            Snackbar.make(relativeLayout, "Cannot have empty input.", Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (editText.getText().toString().length() > 80) {
            Snackbar.make(relativeLayout, "Too many characters.", Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}