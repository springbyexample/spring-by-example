package ua.com.springbyexample.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ua.com.springbyexample.dao.DBConsts.Columns.*;
import static ua.com.springbyexample.dao.provider.EmployeeContentProvider.CONTENT_URI_EMPLOYEE;

/**
 * DAO layer to provider {@link Employee} POJO to {@link EmployeeContentProvider}
 * bridge. Useful when we work with POJOs obtained from REST API layer
 *
 * @author akaverin
 */
public class EmployeeDAO {

    public static void save(Context context, List<Employee> employees) {

        ContentValues[] bulkValues = new ContentValues[employees.size()];
        for (int i = 0; i < employees.size(); ++i) {
            Employee employee = employees.get(i);
            bulkValues[i] = employeeToValues(employee);
            // set default status...
            bulkValues[i].put(SYNC_STATUS,
                    DBConsts.SYNC_STATUS.NOOP.name());
        }

        context.getContentResolver().bulkInsert(CONTENT_URI_EMPLOYEE, bulkValues);
    }

    public static List<Employee> load(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return Collections.emptyList();
        }
        ArrayList<Employee> employees = new ArrayList<Employee>(
                cursor.getCount());

        cursor.moveToFirst();
        do {
            Employee employee = new Employee();
            employee.setFirstName(getStringField(cursor,
                    FIRST_NAME));
            employee.setLastName(getStringField(cursor,
                    SECOND_NAME));
            employee.setProject(getStringField(cursor,
                    PROJECT));
            long serverId = cursor.getLong(cursor
                    .getColumnIndex(ID));
            if (serverId > 0) {
                employee.setId(serverId);
            }

            employees.add(employee);

        } while (cursor.moveToNext());

        // return Collections.unmodifiableList(employees);
        return employees;
    }

    private static ContentValues employeeToValues(Employee employee) {
        ContentValues values = new ContentValues();
        values.put(ID, employee.getId());
        values.put(FIRST_NAME, employee.getFirstName());
        values.put(SECOND_NAME, employee.getLastName());
        values.put(PROJECT, employee.getProject());
        return values;
    }

    private static String getStringField(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

}
